import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateData {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/jdbc";
        String username = "root";
        String password = "aditya";
        String q = ("UPDATE user SET name='Kunal', city='Pune' WHERE id = 6");
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
            System.out.println("Class Loaded Successfully");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection con = DriverManager.getConnection( url, username, password );
            Statement stmt = con.createStatement();
            int affectedRows = stmt.executeUpdate( q );
            if(affectedRows > 0) {
                System.out.println("Data Updated Successfully " + affectedRows + " rows affected");
            } else {
                System.out.println("Data Updation failed");
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
