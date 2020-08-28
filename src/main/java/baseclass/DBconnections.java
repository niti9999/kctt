package baseclass;

import com.microsoft.sqlserver.jdbc.SQLServerCallableStatement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map.Entry;


public class DBconnections extends PageBase{


  /**
   * read data from database.
   *
   * @param storedProcedurename     StoredProcedurename
   * @param spPparametersWithValues SPparametersWithValues
   * @return object of data
   * @throws IOException IOException
   */
  public Object[][] readDataFromDatabaseUsingSp(String storedProcedurename,
      HashMap<String, String> spPparametersWithValues) throws IOException {
    Object[][] tabArray = null;
    int totalRows = 0;
    int totalColumns = 0;
    int ci = 0;
    int cj = 0;

    String serverIP = getConfigProperties("ServerIP");
    String databaseName = getConfigProperties("DatabaseName");
    String userName = getConfigProperties("UserName");
    String password = getConfigProperties("Password");
    String port = getConfigProperties("Port");

    /*
     * Create a variable for the connection string using datas.
     */
    String connectionUrl = "jdbc:sqlserver://" + serverIP + ":" + port
        + ";databaseName=" + databaseName + ";user=" + userName + ";password="
        + password + "";

    try (Connection con = DriverManager.getConnection(connectionUrl);) {
      try (SQLServerCallableStatement stmt = (SQLServerCallableStatement) con
          .prepareCall("{call " + storedProcedurename + " (?)}")) {

        /*
         * parse the parameters and set it for the sp
         */
        for (Entry<String, String> entry : spPparametersWithValues.entrySet()) {
          stmt.setString(entry.getKey(), entry.getValue());
        }

        /*
         * total column
         */
        stmt.execute();
        ResultSetMetaData rsmd = stmt.getMetaData();
        totalColumns = rsmd.getColumnCount();
        System.out.println("Total column : " + totalColumns);

        /*
         * get the row count
         */
        stmt.execute();
        ResultSet rs1 = stmt.getResultSet();
        while (rs1.next()) {
          totalRows = totalRows + 1;
        }

        /*
         * create array
         */
        tabArray = new String[totalRows][totalColumns];

        /*
         * get column & row data
         */
        stmt.execute();
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
          cj = 0;
          for (int j = 1; j <= totalColumns; j++, cj++) {
            tabArray[ci][cj] = rs.getString(rsmd.getColumnName(j));
          }
          ci = ci + 1;
        }

        /*
         * close connections
         */
        rs.close();
        stmt.close();
      } catch (Exception e) {
        System.out.println("exception happened while reading data from database"
            + e.toString());
      }
    } catch (Exception e) {
      /*
       * Handle any errors that may have occurred.
       */
      System.out.println(
          "exception happened while reading data from database" + e.toString());
    }

    /*
     * return object
     */
    return tabArray;
  }

  /**
   * read data from database using query.
   * 
   * @param query query to execute
   * @return object
   * @throws IOException  IO Exception
   * @throws SQLException SQL Exception
   */
  public Object[][] readDataFromDatabaseUsingQuery(String query)
      throws IOException, SQLException {
    
    int totalRows = 0;
    int totalColumns = 0;
    int ci = 0;
    int cj = 0;

    String serverIP = getConfigProperties("ServerIP");
    String databaseName = getConfigProperties("DatabaseName");
    String userName = getConfigProperties("UserName");
    String password = getConfigProperties("Password");
    String port = getConfigProperties("Port");

    /*
     * Create a variable for the connection string using datas.
     */
    String connectionUrl = "jdbc:sqlserver://" + serverIP + ":" + port
        + ";databaseName=" + databaseName + ";user=" + userName + ";password="
        + password + "";

    Connection con = DriverManager.getConnection(connectionUrl);
    Statement stmt = con.createStatement();
    String sql = query;

    /*
     * total column
     */
    ResultSet rs = stmt.executeQuery(sql);
    ResultSetMetaData rsmd = rs.getMetaData();
    totalColumns = rsmd.getColumnCount();
    System.out.println("Total column : " + totalColumns);

    /*
     * get the row count
     */
    ResultSet rs1 = stmt.executeQuery(sql);
    while (rs1.next()) {
      totalRows = totalRows + 1;
    }
    System.out.println("Total rows : " + totalRows);

    /*
     * create array
     */
    Object[][] tabArray = null;
    tabArray = new String[totalRows][totalColumns];

    /*
     * get column & row data
     */
    stmt.execute(sql);
    ResultSet rs2 = stmt.getResultSet();
    while (rs2.next()) {
      cj = 0;
      for (int j = 1; j <= totalColumns; j++, cj++) {
        tabArray[ci][cj] = rs2.getString(rsmd.getColumnName(j));
      }
      ci = ci + 1;
    }

    /*
     * close connections
     */
    rs2.close();
    rs1.close();
    rs.close();
    stmt.close();

    /*
     * return object
     */
    return tabArray;
  }
}
