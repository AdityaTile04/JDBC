import java.sql.*;

public class Preparedstatements {
    public static void main(String[] args) {
        String url= "jdbc:mysql://127.0.0.1:3306/jdbc";
        String username = "root";
        String password = "aditya";
        String q = "SELECT * FROM user WHERE name = ? AND city = ?";
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            System.out.println( e.getMessage() );
        }
        try {
            Connection con = DriverManager.getConnection( url, username, password );
            PreparedStatement ps = con.prepareStatement( q );
            ps.setString( 1, "Aditya" );
            ps.setString( 2, "Bangalore" );
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                System.out.println("ID: " + rs.getInt( "id" ));
                System.out.println("Name: " + rs.getString( "name" ));
                System.out.println("City: " + rs.getString( "city" ));
            }
            ps.close();
            con.close();
            rs.close();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
