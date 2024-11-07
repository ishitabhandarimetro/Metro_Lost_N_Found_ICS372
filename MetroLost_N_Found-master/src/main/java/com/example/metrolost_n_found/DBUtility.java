/**
 * This class provides utility methods for interacting with the database in the MetroLost_n_Found application.
 * It includes methods for user authentication, user registration, adding and deleting reports, and populating lists of reports.
 * The class utilizes JDBC for database connectivity and performs SQL operations such as insertion, deletion, and selection of data.
 * It also handles SQLExceptions by printing stack traces for debugging purposes.
 */

package com.example.metrolost_n_found;

import java.sql.*;
import java.util.ArrayList;

public class DBUtility {
    public static final String DATABASE_URL = "jdbc:sqlite:database.db";
    public static User CurrentSignedInUser = null;

    // Method to sign up a new user
    public static boolean signUp(String email, String name, String password) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            // Establish a database connection
            String sql = "INSERT INTO users (email, name, password) VALUES (?, ?, ?)";
            // Prepare SQL statement to insert a new user
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email); // Set email parameter
                stmt.setString(2, name); // Set name parameter
                stmt.setString(3, password); // Set password parameter
                int rowsAffected = stmt.executeUpdate(); // Execute SQL statement to insert user
                if (rowsAffected > 0) {
                    return true; // Return true for successful sign up
                }
            } //welcome
        } catch (SQLException e) {
            e.printStackTrace(); // Print SQL exception stack trace
        }
        return false; // Return false for unsuccessful sign up
    }

    // Method to verify user duplicate email
    public static String findDuplicateEmail(String email) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            // Establish a database connection
            String sql = "SELECT * FROM users WHERE email = ?";
            // Prepare SQL statement to check email and password
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email); // Set email parameter
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // If duplicate email found, return it
                        return email;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print SQL exception stack trace
        }
        return null; // Return null if unable to find duplicate due to exception or other reasons
    }

    // Method to verify user login credentials
    public static boolean verifyLogin(String email, String password) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            // Establish a database connection
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            // Prepare SQL statement to check email and password
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email); // Set email parameter
                stmt.setString(2, password); // Set password parameter
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // If credentials match, set currentLoggedInUser
                        int userID = rs.getInt("user_id");
                        String name = rs.getString("name");
                        CurrentSignedInUser = new User(userID, email, name); // Create User object
                        return true; // Return true for successful login
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print SQL exception stack trace
        }
        return false; // Return false for unsuccessful login
    }


    // Method to add a new report to the database
    public static boolean addReport(String description, String category, String location, String contact) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            // Establish a database connection
            String sql = "INSERT INTO reports (description, category, location, contact, user_id) VALUES (?, ?, ?, ?, ?)";
            // Prepare SQL statement to insert a new report
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, description); // Set description parameter
                stmt.setString(2, category); // Set category parameter
                stmt.setString(3, location); // Set location parameter
                stmt.setString(4, contact); // Set contact parameter
                stmt.setInt(5, CurrentSignedInUser.getUserID()); // Set user ID parameter
                stmt.executeUpdate(); // Execute SQL statement to insert report
                return true; //Return true for successful addReport
            }
        } catch (SQLException e) {
            e.printStackTrace(); //Print SQL exception stack trace
        }

        return false; //Return false for unsuccessful addReport
    }


    // Method to delete a report from the database
    public static boolean deleteReport(int reportID) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            // Establish a database connection
            String sql = "DELETE FROM reports WHERE report_id = ?";
            // Prepare SQL statement to delete a report
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, reportID); // Set report ID parameter
                stmt.executeUpdate(); // Execute SQL statement to delete report
                return true; //Return true for successful deleteReport
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print SQL exception stack trace
        }
        return false; //Return false for unsuccessful deleteReport
    }

    // Method to populate list of user's reports
    public static ArrayList<Report> populateMyReportsList() {
        ArrayList<Report> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            // Establish a database connection
            String sql = "SELECT * FROM reports WHERE user_id = ?";
            // Prepare SQL statement to select reports of the current user
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, CurrentSignedInUser.getUserID()); // Set user ID parameter
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        // Iterate through result set and add reports to list
                        int reportID = rs.getInt("report_id");
                        String description = rs.getString("description");
                        String category = rs.getString("category");
                        String location = rs.getString("location");
                        String contact = rs.getString("contact");
                        Report report = new Report(reportID, description, category, location, contact); // Create Report object
                        list.add(report); // Add report to list
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print SQL exception stack trace
        }
        return list;
    }


    // Method to populate list of all reports
    public static ArrayList<Report> populateAllReportsList() {
        ArrayList<Report> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            // Establish a database connection
            String sql = "SELECT * FROM reports";
            // Prepare SQL statement to select all reports
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        // Iterate through result set and add reports to list
                        int reportID = rs.getInt("report_id");
                        String description = rs.getString("description") ;
                        String category = rs.getString("category");
                        String location = rs.getString("location");
                        String contact =  rs.getString("contact");
                        Report report = new Report(reportID, description, category, location, contact); // Create Report object
                        list.add(report); // Add report to list
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print SQL exception stack trace
        }
        return list;
    }


    // Method to populate list of filtered reports
    public static ArrayList<Report> populateFilteredReportsList(String category) {
        ArrayList<Report> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            // Establish a database connection
            String sql = "SELECT * FROM reports WHERE category = ?";
            // Prepare SQL statement to select reports based on category
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, category); // Set category parameter
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        // Iterate through result set and add reports to list
                        int reportID = rs.getInt("report_id");
                        String description = rs.getString("description");
                        String location =  rs.getString("location");
                        String contact = rs.getString("contact");
                        Report report = new Report(reportID, description, category, location, contact); // Create Report object
                        list.add(report); // Add report to list
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print SQL exception stack trace
        }
        return list;
    }
}
