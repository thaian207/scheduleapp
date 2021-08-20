package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * class to connect to SQL database
 */
public class DBConnection {
    private static Connection connect;
    //login information to SQL DB
    private static final String DBName = "WJ06k6C";
    private static final String DBUrl = "jdbc:mysql://wgudb.ucertify.com/" + DBName;
    private static final String userName = "U06k6C";
    private static final String password = "53688791438";
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    /**
     * initiate connection to DB
     * @return connection to DB
     */
    public static Connection getConnection(){
        try {
            Class.forName(driver);
            connect = DriverManager.getConnection(DBUrl, userName, password);
            System.out.println("Connection Successful");
            } catch(Exception e){
                e.printStackTrace();
                e.getCause();
            }return connect;
        }

    /**
     * Disconnect from DB
     * @throws SQLException
     */
    public void DBDisconnect() throws SQLException {
        connect.close();
        System.out.println("Disconnected from Database");
    }
}
