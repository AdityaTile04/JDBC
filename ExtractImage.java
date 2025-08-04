import java.io.*;
import java.sql.*;

public class ExtractImage {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/jdbc";
        String user = "root";
        String pass = "aditya";
        String q = "select image_data from image_table where image_id = (?)";
        String folder_path = "D:\\Image\\img\\";
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver" );
            System.out.println( "Class Loaded Successfully" );
        } catch (ClassNotFoundException e) {
            System.out.println( e.getMessage() );
        }
        try {
            Connection con = DriverManager.getConnection( url, user, pass );
            PreparedStatement ps = con.prepareStatement( q );
            ps.setInt( 1, 1 );
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                byte[] image_data = rs.getBytes( "image_data" );
                String image_path = folder_path + "extracted.png";
                OutputStream os = new FileOutputStream( image_path );
                os.write( image_data );
                System.out.println("Image Extracted Succesfully");
            } else {
                System.out.println("Failed to extract image");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException( e );
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
    }
}

