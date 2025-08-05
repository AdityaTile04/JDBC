package Projects.Banking_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private Connection connection;
    private Scanner scanner;

    public User(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }
    public void register() {
        scanner.nextLine();
        System.out.println("Full Name: ");
        String full_name = scanner.nextLine();
        System.out.println("Email: ");
        String email = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        if(user_exist(email)) {
            System.out.println("User Already Exists for this Email Address!!");
            return;
        }
        String register_query = "insert into user(full_name, email, password) values(?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement( register_query );
            ps.setString(1, full_name  );
            ps.setString( 2, email );
            ps.setString( 3, password );
            int affectedRows = ps.executeUpdate();
            if(affectedRows > 0) {
                System.out.println("User Register Successfully!😊");
            } else {
                System.out.println("Registration Failed");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public String login() {
        scanner.nextLine();
        System.out.println("Email: ");
        String email = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        String login_query = "select * from user where email = ? and password = ?";
        try {
            PreparedStatement ps = connection.prepareStatement( login_query );
            ps.setString( 1, email );
            ps.setString( 2, password );
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return email;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public boolean user_exist(String email) {
        String q = "select * from user where email = ?";
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
