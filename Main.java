import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/college";
        String username = "root";
        String password = "aditya";

        try(Connection connection = DriverManager.getConnection( url, username, password )) {
            System.out.println("Connected to database");
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e.getMessage());
        }

    }
}
