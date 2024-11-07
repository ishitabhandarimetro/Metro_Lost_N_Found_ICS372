/**
 * This class serves as the main entry point for the MetroLost_n_Found application.
 * It extends the JavaFX Application class and provides the start method for initializing the application.
 * The start method sets up the initial scene by loading the Login.fxml file.
 * The main method initializes the database tables if they do not exist and launches the application.
 * It utilizes JavaFX for UI components and scene management and JDBC for database operations.
 */

package com.example.metrolost_n_found;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.metrolost_n_found.DBUtility.DATABASE_URL;

public class MainApplication extends Application {

    // Start method to initialize the application to the login page
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Set up initial scene with Login.fxml and display it
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.show();
    }

    // Main method to initialize the database tables and launch the application
    public static void main(String[] args) {

        // Creates the USERS table in the database if it has not already been created
        String SQLCreateUsersCode = "CREATE TABLE IF NOT EXISTS users (user_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, password TEXT)";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(SQLCreateUsersCode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Creates the REPORTS table in the database if it has not already been created
        String SQLCreateReportsCode = "CREATE TABLE IF NOT EXISTS reports ( report_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INT, description TEXT, category TEXT, location TEXT, contact TEXT)";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(SQLCreateReportsCode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Launches the application
        launch();
    }
}
