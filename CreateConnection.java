import java.sql.*;

public class CreateConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/jdbc";
        String username = "root";
        String password = "aditya";

        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection con = DriverManager.getConnection( url, username, password );
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM user" );

            while (rs.next()) {
                System.out.println();
                System.out.println("======================");
                System.out.println("ID: " + rs.getInt( "id" ));
                System.out.println("Name: " + rs.getString( "name" ));
                System.out.println("City: " + rs.getString( "city" ));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}