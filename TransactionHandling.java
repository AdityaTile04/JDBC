import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionHandling {
    public static void main(String[] args) {
        String url= "jdbc:mysql://127.0.0.1:3306/jdbc";
        String username = "root";
        String password = "aditya";
        String withdrawQuery = "update accounts set balance = balance - ? where account_number = ?";
        String depositQuery = "update accounts set balance = balance + ? where account_number = ?";
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver" );
            System.out.println("Class Loaded Successfully");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection con = DriverManager.getConnection( url, username, password );
            con.setAutoCommit( false );
            try {
                PreparedStatement ws = con.prepareStatement( withdrawQuery );
                PreparedStatement ds = con.prepareStatement( depositQuery );
                ws.setDouble( 1, 500.00 );
                ws.setString(2, "account789"  );
                ds.setDouble( 1, 500.00 );
                ds.setString( 2, "account123" );
              int arw = ws.executeUpdate();
              int ard = ds.executeUpdate();
              if(arw > 0 && ard > 0) {
                  con.commit();
                  System.out.println("Transaction complete successfully");
              } else {
                  con.rollback();
                  System.out.println("Transaction Failed");
              }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
