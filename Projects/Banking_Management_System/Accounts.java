package Projects.Banking_Management_System;

import java.sql.*;
import java.util.Scanner;

public class Accounts {
    private Connection connection;
    private Scanner scanner;

    public Accounts(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public long open_account(String email) {
        if(!account_exist(email)) {
            String open_account_query = "insert into accounts(account_number, full_name,email, balance, security_pin) values (?, ?, ?, ?, ?)";
            scanner.nextLine();
            System.out.println("Enter Full Name: ");
            String full_name = scanner.nextLine();
            System.out.println("Enter Initial Amount: ");
            double balance = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Enter Security Pin: ");
            String security_pin = scanner.nextLine();
            try {
                long account_number = generateAccountNumber();
                PreparedStatement ps = connection.prepareStatement( open_account_query );
                ps.setLong( 1, account_number );
                ps.setString( 2, full_name );
                ps.setString( 3, email );
                ps.setDouble( 4, balance );
                ps.setString( 5, security_pin );
                int affected_rows = ps.executeUpdate();
                if(affected_rows > 0) {
                    return account_number;
                } else {
                    throw new RuntimeException("Account creation failed!!");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        throw new RuntimeException("Account already Exist");
    }
    public long getAccountNumber(String email) {
        String q = "select account_number from accounts where email = ?";
        try {
            PreparedStatement ps = connection.prepareStatement( q );
            ps.setString( 1, email );
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getLong( "account_number" );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException("Account Number Doesn't Exist!");
    }
    private long generateAccountNumber() {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery( "SELECT account_number from Accounts ORDER BY account_number DESC LIMIT 1" );
            if(rs.next()) {
                long last_account_number = rs.getLong( "account_number" );
                return last_account_number + 1;
            } else {
                return 10000100;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 10000100;
    }
    public boolean account_exist(String email) {
        String q = "SELECT account_number from Accounts WHERE email = ?";
        try {
            PreparedStatement ps = connection.prepareStatement( q );
            ps.setString( 1, email );
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
