import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteUsingPS {
    public static void main(String[] args) {
        Scanner sc = new Scanner( System.in );
        String url = "jdbc:mysql://127.0.0.1:3306/jdbc";
        String username = "root";
        String password = "aditya";
        String q = "DELETE FROM user WHERE id = ?";
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection con = DriverManager.getConnection( url, username, password );
            PreparedStatement ps = con.prepareStatement( q );
            System.out.println("Enter user ID: ");
            int id = sc.nextInt();
            ps.setInt( 1, id );
            int ar = ps.executeUpdate();
            if(ar > 0) {
                System.out.println("Data Deleted Successfully");
            } else {
                System.out.println("Data deletion failed");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
