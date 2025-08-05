import java.sql.*;
import java.util.Scanner;

public class BatchProcessing {
    public static void main(String[] args) {
        Scanner sc = new Scanner( System.in );
        String url = "jdbc:mysql://127.0.0.1:3306/jdbc";
        String user = "root";
        String pass = "aditya";
        String q1 = "insert into student (id,name,subject) values (?,?,?)";
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver" );
            System.out.println("Class loaded successfully");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection con = DriverManager.getConnection( url, user, pass );
            con.setAutoCommit( false );
            PreparedStatement ps = con.prepareStatement( q1 );
            while (true) {
                System.out.println("Enter ID: ");
                int id = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter Name: ");
                String name = sc.nextLine();
                System.out.println("Enter Subject: ");
                String sub = sc.nextLine();
                ps.setInt( 1, id );
                ps.setString( 2,name );
                ps.setString( 3, sub );
                ps.addBatch();
                System.out.println("Add more values Y / N: ");
                String decision = sc.nextLine();
                if(decision.toUpperCase().equals( "N" )) {
                    break;
                }
            }
            int res[] = ps.executeBatch();
            con.commit();
            System.out.println("Batch executed successfully");
            Statement stmt = con.createStatement();
            stmt.addBatch( q1 );
//            stmt.addBatch( q2 );
//            stmt.addBatch( q3 );
            int[] result = stmt.executeBatch( );
            con.commit();
            System.out.println("Batch executed successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
