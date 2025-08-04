import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertUsingPS {
    public static void main(String[] args) {
        String url= "jdbc:mysql://127.0.0.1:3306/jdbc";
        String username = "root";
        String password = "aditya";
        String q = "INSERT INTO user (id, name, city) VALUES (?,?,?)";
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            System.out.println( e.getMessage() );
        }
        try {
            Connection con = DriverManager.getConnection( url,username,password );
            PreparedStatement ps = con.prepareStatement( q );
            ps.setInt(1,  8 );
            ps.setString( 2, "Aniket" );
            ps.setString( 3, "Palse" );

            int ar = ps.executeUpdate();
            if(ar > 0) {
                System.out.println("Data Inserted Successfully");
            } else {
                System.out.println("Data insertion failed");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
