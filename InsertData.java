import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertData {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/jdbc";
        String username = "root";
        String password = "aditya";
        String q = "INSERT INTO user (id, name, city) VALUES (6, 'Gaurav', 'Nashik')";

        try {
            Class.forName( "com.mysql.jdbc.Driver" );
            System.out.println("Driver Loaded Successfully");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection con = DriverManager.getConnection( url, username, password );
            Statement stmt = con.createStatement();
            int rowsAffected = stmt.executeUpdate( q );
            if(rowsAffected > 0) {
                System.out.println("Data Inserted Successfully " + rowsAffected + " rows affected");
            } else {
                System.out.println("Data insertion failed");
            }
            stmt.close();
            con.close();
            System.out.println("Resources closed successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
