/*
 * Slightly modified version of the com.ibatis.common.jdbc.ScriptRunner class
 * from the iBATIS Apache project. Only removed dependency on Resource class
 * and a constructor
 */
/*
 *  Copyright 2004 Clinton Begin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Tool to run database scripts
 */
public class ScriptRunner {

  private static final String DEFAULT_DELIMITER = ";";

  private Connection connection;

  private boolean stopOnError;

  private PrintWriter logWriter;
  private PrintWriter errorLogWriter;

  private String delimiter = DEFAULT_DELIMITER;
  private boolean fullLineDelimiter = false;

  private List<QueryResult> results;

  /**
   * Default constructor
   */
  public ScriptRunner(Connection connection) {
    this.connection = connection;
    this.stopOnError = true;
    this.results = new LinkedList<>();
  }

  public void setDelimiter(String delimiter, boolean fullLineDelimiter) {
    this.delimiter = delimiter;
    this.fullLineDelimiter = fullLineDelimiter;
  }

  /**
   * Setter for logWriter property
   *
   * @param logWriter - the new value of the logWriter property
   */
  public void setLogWriter(PrintWriter logWriter) {
    this.logWriter = logWriter;
  }

  /**
   * Setter for errorLogWriter property
   *
   * @param errorLogWriter - the new value of the errorLogWriter property
   */
  public void setErrorLogWriter(PrintWriter errorLogWriter) {
    this.errorLogWriter = errorLogWriter;
  }

  /**
   * Runs an SQL script (read in using the Reader parameter)
   *
   * @param reader - the source of the script
   */
  public void runScript(Reader reader) throws IOException, SQLException {
    try {
      runScript(connection, reader, false);
    } catch (IOException e) {
      throw e;
    } catch (SQLException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException("Error running script.  Cause: " + e, e);
    }
  }

  /**
   * Runs an SQL script (read in using the Reader parameter) using the
   * connection passed in
   *
   * @param conn   - the connection to use for the script
   * @param reader - the source of the script
   * @throws SQLException if any SQL errors occur
   * @throws IOException  if there is an error reading from the Reader
   */
  private void runScript(Connection conn, Reader reader, boolean comparisonOnly) throws IOException,
    SQLException {
    StringBuffer command = null;
    int queryId = 0;
    try {
      LineNumberReader lineReader = new LineNumberReader(reader);
      String line = null;
      while ((line = lineReader.readLine()) != null) {
        if (command == null) {
          command = new StringBuffer();
        }
        String trimmedLine = line.trim();
        if (trimmedLine.startsWith("--")) {
          println(trimmedLine);
        } else if (trimmedLine.length() < 1
          || trimmedLine.startsWith("//")) {
          // Do nothing
        } else if (trimmedLine.length() < 1
          || trimmedLine.startsWith("--")) {
          // Do nothing
        } else if (!fullLineDelimiter
          && trimmedLine.endsWith(getDelimiter())
          || fullLineDelimiter
          && trimmedLine.equals(getDelimiter())) {
          queryId++;
          command.append(line.substring(0, line.lastIndexOf(getDelimiter())));
          command.append(" ");
          executeCommand(conn, comparisonOnly, command, queryId, line);
          command = null;
          Thread.yield();
        } else {
          command.append(line);
          command.append(" ");
        }
        flush();
      }
      if (command != null && !command.toString().trim().isEmpty()) {
        queryId++;
        executeCommand(conn, comparisonOnly, command, queryId, line);
      }
    } catch (SQLException e) {
      e.fillInStackTrace();
      flush();
      printlnError("Error executing Query " + queryId + ": " + command);
      printlnError(e);
      throw e;
    } catch (IOException e) {
      e.fillInStackTrace();
      flush();
      printlnError("Error executing: " + command);
      printlnError(e);
      throw e;
    } finally {
      flush();
    }
  }

  private void executeCommand(Connection conn, boolean comparisonOnly, StringBuffer command, int queryId, String line) throws SQLException {
    println("====================================================");
    if (!comparisonOnly) {
      println("Query " + queryId);
    } else {
      println("Check Query " + queryId);
    }
    Statement statement = conn.createStatement();
    if (!comparisonOnly) {
      println(command);
    }
    StringBuilder resultContent = new StringBuilder();
    boolean hasResults = false;
    if (stopOnError) {
      hasResults = statement.execute(command.toString());
    } else {
      try {
        statement.execute(command.toString());
      } catch (SQLException e) {
        e.fillInStackTrace();
        printlnError("Error executing: " + command);
        printlnError(e);
      }
    }

    ResultSet rs = statement.getResultSet();
    if (hasResults && rs != null) {
      println("----------------------------------------------------");
      ResultSetMetaData md = rs.getMetaData();
      int cols = md.getColumnCount();
      for (int i = 0; i < cols; i++) {
        String name = md.getColumnLabel(i + 1);
        resultContent.append(name + "\t|\t");
        print(name + "\t|\t");
      }
      resultContent.append("\r\n");
      println("");
      int count = 0;
      while (rs.next()) {
        for (int i = 0; i < cols; i++) {
          String value = rs.getString(i + 1).replaceAll("\r", "").replaceAll("\n", "");
          resultContent.append(value + "\t|\t");
          if ((!comparisonOnly && count < 100) || (comparisonOnly && count < 10)) {
            print(value + "\t|\t");
          }
        }
        resultContent.append("\r\n");
        if ((!comparisonOnly && count < 100) || (comparisonOnly && count < 10)) {
          println("");
        }
        count++;
      }
      if (comparisonOnly && count > 10 || count > 100) {
        println("[[More Entries Hidden]]\r\n");
      }
      println("----------------------------------------------------");
      println("Returned " + count + " rows with " + cols + " columns.");
      QueryResult result = new QueryResult(queryId, command.toString(), cols, count, resultContent.toString());
      if (comparisonOnly) {
        result.setHidden(true);
      }
      results.add(result);
    } else {
      println("Updated " + statement.getUpdateCount() + " rows.");
    }

    command = null;
    try {
      statement.close();
    } catch (Exception e) {
    }
  }

  private String getDelimiter() {
    return delimiter;
  }

  private void print(Object o) {
    if (logWriter != null) {
      logWriter.print(o);
    }
  }

  private void println(Object o) {
    if (logWriter != null) {
      logWriter.println(o);
    }
  }

  private void printlnError(Object o) {
    if (errorLogWriter != null) {
      errorLogWriter.println(o);
    }
  }

  private void flush() {
    if (logWriter != null) {
      logWriter.flush();
    }
    if (errorLogWriter != null) {
      errorLogWriter.flush();
    }
  }

  public List<QueryResult> getResults() {
    return results;
  }

  public void clearResults() {
    results.clear();
  }

  public void runComparisonScript(FileReader reader) throws IOException, SQLException {
    try {
        runScript(connection, reader, true);
    } catch (IOException e) {
      throw e;
    } catch (SQLException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException("Error running script.  Cause: " + e, e);
    }
  }
}
