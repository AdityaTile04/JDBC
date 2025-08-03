package Projects;

import java.sql.*;
import java.util.Scanner;

public class HotelReservationSystem {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "aditya";

    public static void main(String[] args) {
        try {
            Class.forName( "com.mysql.cj.dbc.Driver" );
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection con = DriverManager.getConnection( url, username, password );
            while(true) {
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                Scanner sc = new Scanner( System.in );
                System.out.println("1. Reverse a room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservations");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        reserveRoom(con, sc);
                        break;
                    case 2:
                        viewReservation(con);
                        break;
                    case 3:
                        getRoomNumber(con, sc);
                        break;
                    case 4:
                        updateReservation(con, sc);
                        break;
                    case 5:
                        deleteReservation(con, sc);
                        break;
                    case 0:
                        exit();
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid Choice. Try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private static void reserveRoom(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter guest name: ");
            String guestName = scanner.next();
            scanner.nextLine();
            System.out.println("Enter room number: ");
            int roomNumber = scanner.nextInt();
            System.out.println("Enter contact number: ");
            String contactNumber = scanner.next();
            String query = "INSERT INTO reservations (guest_name, room_number, contact_number) " +
                    "VALUES ('" + guestName + "', " + roomNumber + ", '" + contactNumber + "')";

            try (Statement stmt = connection.createStatement()) {
                int affectedRows = stmt.executeUpdate( query );
                if(affectedRows > 0) {
                    System.out.println("Reservation Successful!");
                } else {
                    System.out.println("Reservation Failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewReservation(Connection connection) {
        String query = "SELECT reservation_id, guest_name, room_number, contact_number, reservation_date FROM reservations";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery( query );

            System.out.println("Current Reservations:");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            System.out.println("| Reservation ID | Guest           | Room Number   | Contact Number      | Reservation Date        |");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

            while (rs.next()) {
                int reservationId = rs.getInt("reservation_id");
                String guestName = rs.getString("guest_name");
                int roomNumber = rs.getInt("room_number");
                String contactNumber = rs.getString("contact_number");
                String reservationDate = rs.getTimestamp("reservation_date").toString();

                System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n",
                        reservationId, guestName, roomNumber, contactNumber, reservationDate);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
    }

    private static void getRoomNumber(Connection connection, Scanner scanner) {
        System.out.println("Enter Reservation ID: ");
        int reservationId = scanner.nextInt();
        System.out.println("Enter guest name: ");
        String guestName = scanner.next();

        String query =  "SELECT room_number FROM reservations " +
                "WHERE reservation_id = " + reservationId +
                " AND guest_name = '" + guestName + "'";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery( query );
            if(rs.next()) {
                int roomNumber = rs.getInt( "room_number" );
                System.out.println("Room number for Reservation ID " + reservationId + " and Guest " + guestName + " is: " + roomNumber);
            } else {
                System.out.println("Reservation not found for the given ID and guest name.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateReservation(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter reservation ID to update: ");
            int reservationId = scanner.nextInt();
            scanner.nextLine();
            if(!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for the given ID.");
                return;
            }
            System.out.println("Enter new guest name: ");
            String newGuestName = scanner.nextLine();
            System.out.println("Enter new room number: ");
            int newRoomNumber = scanner.nextInt();
            System.out.println("Enter new contact number: ");
            String newContactNumber = scanner.next();

            String query = "UPDATE reservations SET guest_name = '" + newGuestName + "', " +
                    "room_number = " + newRoomNumber + ", " +
                    "contact_number = '" + newContactNumber + "' " +
                    "WHERE reservation_id = " + reservationId;

            try {
                Statement stmt = connection.createStatement();
                int affectRows = stmt.executeUpdate( query );
                if(affectRows > 0) {
                    System.out.println("Reservation updated successfully");
                } else {
                    System.out.println("Reservation update failed");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteReservation(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter reservation ID to delete: ");
            int reservationId = scanner.nextInt();
            if(!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for the given ID.");
                return;
            }
            String query = "DELETE FROM reservations WHERE reservation_id = " + reservationId;
          try {
              Statement stmt = connection.createStatement();
              int affectedRows = stmt.executeUpdate( query );
              if(affectedRows > 0) {
                  System.out.println("Reservation deleted successfully!");
              } else {
                  System.out.println("Reservation deletion failed.");
              }
          } catch (SQLException e) {
              System.out.println(e.getMessage());
          }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private static boolean reservationExists(Connection connection, int reservation_id) {
        try {
            String query = "SELECT reservation_id FROM reservations WHERE reservation_id = " + reservation_id;
            try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery( query )){
                return rs.next();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void exit() throws InterruptedException {
        System.out.println("Exiting System");
        int i = 5;
        while(i!=0) {
            System.out.print(".");
            Thread.sleep( 1000 );
            i--;
        }
        System.out.println();
        System.out.println("Thank you for using hotel reservation system.!!!!");
    }
}
