/**
 * This class represents a query that returned a result.
 *
 * @author Loïc Ortola on 29/01/2017
 */
public class QueryResult {
  public final int id;
  public final String queryString;
  public final int columnCount;
  public final int rowCount;
  public final String result;
  private boolean hidden;

  public QueryResult(int id, String queryString, int columnCount, int rowCount, String result) {
    this.id = id;
    this.queryString = queryString;
    this.columnCount = columnCount;
    this.rowCount = rowCount;
    this.result = result;
  }

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }

  public boolean isHidden() {
    return hidden;
  }
}
