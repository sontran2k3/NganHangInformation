package Context;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Admin
 */
public class DBContext {

    private static final String userID = "root";
    private static final String password = "1234";
    private static final String dbName = "nganhang";
    private static final String serverName = "localhost";
    private static final String portNumber = "3306";

    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        return DriverManager.getConnection(url, userID, password);
    }

    public static void main(String[] args) {
        try {
            System.out.println(new DBContext().getConnection());
            System.out.println("Kết Nối Thành Công");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Kết Nối Thất Bại");
        }
    }
}
