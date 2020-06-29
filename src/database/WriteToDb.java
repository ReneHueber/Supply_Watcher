package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WriteToDb {

    /**
     * Executes a Sql Statement to inset into a table or update a table.
     * @param sqlStatement Sql Statement for the Table
     * @param values Required Parameters for the Sql Statement
     */
    public static void executeWriteStmt(String sqlStatement, String... values){
        try (Connection conn = Connect.connectDb()) {
            assert conn != null;
            try (PreparedStatement prepStatement =
                    conn.prepareStatement(sqlStatement)){
                // goes throw all the passed Parameter's
                for(int i = 0; i < values.length; i++){
                    prepStatement.setString(i + 1, values[i]);
                }
                prepStatement.execute();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
