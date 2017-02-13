import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Database Query comparator.
 *
 * @author Loïc Ortola on 29/01/2017
 */
public class DBComparator {

  private static final String DRIVER = "db.driver";
  private static final String HOST = "db.host";
  private static final String PORT = "db.port";
  private static final String DB_NAME = "db.name";
  private static final String USER = "db.user";
  private static final String PASSWORD = "db.password";
  private static final String EXPECTED_QUERY_PATH = "query.expected.path";
  private static final String TEST_QUERY_PATH = "query.test.path";
  private static final String COMPARISON_ENABLED = "query.compare.enabled";
  public static final String COMPARISON_QUERY_PATH = "query.compare.path";

  private Properties props;
  private boolean comparisonEnabled;

  public DBComparator() {
    props = new Properties();
    // Load properties
    try (final InputStream stream = this.getClass().getClassLoader().getResourceAsStream("db.properties")) {
      props.load(stream);
    } catch (IOException e) {
      throw new IllegalStateException("Error while loading properties. Please check your db properties are fine");
    }
    // Load driver
    try {
      Class.forName(props.getProperty(DRIVER));
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException("Driver not supported: " + props.getProperty(DRIVER));
    }
    // Check if comparison enabled
    if (props.containsKey(COMPARISON_ENABLED) && "true".equals(props.getProperty(COMPARISON_ENABLED))) {
      comparisonEnabled = true;
    }
  }

  private Connection getConnection() {
    try {
      Connection c = DriverManager.getConnection(props.getProperty(HOST) + ":" + props.getProperty(PORT) + "/" + props.getProperty(DB_NAME), props.getProperty(USER), props.getProperty(PASSWORD));
      c.setAutoCommit(false); // Force to disable auto commit
      return c;
    } catch (SQLException e) {
      throw new IllegalStateException("Cannot connect to remote database", e);
    }
  }

  public void compare() {

    if (!Files.exists(getExpectedQueryFile())) {
      throw new IllegalStateException("Expected query file cannot be found. Please check your db properties are fine");
    }
    if (!Files.exists(getTestQueryFile())) {
      throw new IllegalStateException("Test query file cannot be found. Please check your db properties are fine");
    }
    if (comparisonEnabled && !Files.exists(getComparisonQueryFile())) {
      throw new IllegalStateException("Test comparison query file cannot be found. Please check your db properties are fine");
    }

    // Get expected content
    List<QueryResult> expectedResults = runExpected();
    // Get test content
    List<QueryResult> testResults = runTarget();

    try {
      if (expectedResults.size() > 1) {
        compareEach(expectedResults, testResults);
      } else if (expectedResults.size() == 1 && testResults.size() >= 1) {
        compareOne(expectedResults.get(0), testResults.get(testResults.size() - 1));
      } else {
        System.err.println("You must make at least one SELECT query to compare the expected results with the user target.");
        throw new IllegalStateException("You must make at least one SELECT query to compare the expected results with the user target.");
      } 
    } catch (IllegalStateException e) {
      System.exit(1);
    }
    System.out.println("Everything went well!");
  }

  private void compareOne(QueryResult expectedResult, QueryResult testResult) {
    if (expectedResult.columnCount != testResult.columnCount) {
      throwError("Wrong column count in SELECT statement results.", testResult, expectedResult.columnCount, testResult.columnCount);
    }
    if (expectedResult.rowCount != testResult.rowCount) {
      throwError("Wrong result count in SELECT statement results.", testResult, expectedResult.rowCount, testResult.rowCount);
    }
    if (!expectedResult.result.equals(testResult.result)) {
      throwError("The results don't match.", testResult, expectedResult.result, testResult.result);
    }
  }

  private void compareEach(List<QueryResult> expectedResults, List<QueryResult> testResults) {
    if (expectedResults.size() != testResults.size()) {
      throwError("Your count of SELECT queries is not what is expected.", expectedResults.size(), testResults.size());
    }
    for (int i = 0; i < expectedResults.size(); i++) {
      compareOne(expectedResults.get(i), testResults.get(i));
    }
  }

  private void throwError(String message, Object expectedContent, Object foundContent) {
    System.err.println("====================================================");
    System.err.println("An error occured: " + message);
    System.err.println("Expected:" + expectedContent);
    System.err.println("Actual:" + foundContent);
    System.err.println("====================================================");
    throw new IllegalStateException(message);
  }

  private void throwError(String message, QueryResult found, Object expectedContent, Object foundContent) {
    System.err.println("====================================================");
    System.err.println("An error occured: " + message);
    if (!found.isHidden()) {
      System.err.println("Query id: " + found.id);
      System.err.println("Query: " + found.queryString);
    }
      System.err.println("Expected: " + expectedContent);
      System.err.println("Actual: " + foundContent);
    System.err.println("====================================================");
    throw new IllegalStateException(message);
  }

  private List<QueryResult> runExpected() {
    Connection conn = getConnection();
    ScriptRunner expectedRunner = new ScriptRunner(conn);
    try {
      expectedRunner.runScript(new FileReader(getExpectedQueryFile().toFile()));
      if (comparisonEnabled) {
        // Clear results
        expectedRunner.clearResults();
        expectedRunner.runComparisonScript(new FileReader(getComparisonQueryFile().toFile()));
      }
    } catch (IOException e) {
      throw new IllegalStateException(e);
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    } finally {
      rollback(conn);
      close(conn);
    }
    return expectedRunner.getResults();
  }

  private List<QueryResult> runTarget() {
    Connection conn = getConnection();
    ScriptRunner expectedRunner = new ScriptRunner(conn);
    expectedRunner.setLogWriter(new PrintWriter(System.out));
    expectedRunner.setErrorLogWriter(new PrintWriter(System.err));
    try {
      expectedRunner.runScript(new FileReader(getTestQueryFile().toFile()));
      if (comparisonEnabled) {
        // Clear results
        expectedRunner.clearResults();
        expectedRunner.runComparisonScript(new FileReader(getComparisonQueryFile().toFile()));
      }
    } catch (IOException e) {
      throw new IllegalStateException(e);
    } catch (SQLException e) {
      System.exit(1);
    } finally {
      rollback(conn);
      close(conn);
    }
    return expectedRunner.getResults();
  }

  private void rollback(Connection conn) {
    try {
      conn.rollback();
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  private void close(Connection conn) {
    try {
      conn.close();
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    }
  }

  private Path getExpectedQueryFile() {
    return Paths.get(props.getProperty(EXPECTED_QUERY_PATH));
  }

  private Path getTestQueryFile() {
    return Paths.get(props.getProperty(TEST_QUERY_PATH));
  }

  private Path getComparisonQueryFile() {
    return Paths.get(props.getProperty(COMPARISON_QUERY_PATH));
  }

}
