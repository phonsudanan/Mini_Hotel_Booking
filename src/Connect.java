import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    static String url = "jdbc:mysql://localhost/minihotel";
    static String user = "root";
    static  String pass = "12345678";
    static  String className = "com.mysql.cj.jdbc.Driver";

    public static Connection ConnectDB() {

        try {
            Class.forName(className);
            Connection con = DriverManager.getConnection(url, user, pass);
//            System.out.println("Database Connected.");
            return con;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
