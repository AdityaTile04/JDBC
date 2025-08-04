import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdationUsingPS {
    public static void main(String[] args) {
        Scanner sc = new Scanner( System.in );
        String url = "jdbc:mysql://127.0.0.1:3306/jdbc";
        String username = "root";
        String password = "aditya";
        String q = "UPDATE user SET name= ?, city= ? WHERE id = ?";
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection con = DriverManager.getConnection( url, username, password );
            PreparedStatement ps = con.prepareStatement( q );
            System.out.println("Enter Name: ");
            String name = sc.nextLine();
            System.out.println("Enter City: ");
            String city = sc.nextLine();
            System.out.println("Enter ID: ");
            int id = sc.nextInt();
            ps.setString( 1, name );
            ps.setString( 2,  city );
            ps.setInt( 3, id );
            int ar = ps.executeUpdate();
            if(ar > 0) {
                System.out.println("Data Updated");
            } else {
                System.out.println("Data updation failed");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
