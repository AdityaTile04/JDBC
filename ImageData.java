import java.io.*;
import java.sql.*;

public class ImageData {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/jdbc";
        String username = "root";
        String password = "aditya";
//        String q = "SELECT image_data FROM image_table WHERE image_id = (?)";
        String q = "insert into image_table (image_data) values (?)";
        String path = "C:\\Users\\adity\\Downloads\\ujj.jpg";
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver" );
            System.out.println("Class Loaded Successfully");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection con = DriverManager.getConnection( url, username, password );
            PreparedStatement ps = con.prepareStatement( q );
            FileInputStream fs = new FileInputStream( path );
            byte[] image_data = new byte[fs.available()];
            fs.read(image_data);
            ps.setBytes( 1, image_data );
            int ar = ps.executeUpdate();
            if(ar > 0) {
                System.out.println("Image Added Successfully");
            } else {
                System.out.println("Image insertion failed!!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
