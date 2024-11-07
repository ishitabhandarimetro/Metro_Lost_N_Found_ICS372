/**
 * This class serves as the controller for the login screen.
 * It implements the SceneChanger interface for changing scenes.
 * The class provides methods for handling login actions, displaying password visibility, and navigating to the signup screen or dashboard.
 * It utilizes JavaFX for UI components and scene management and JOptionPane for displaying messages.
 */

package com.example.metrolost_n_found;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginController implements SceneChanger{

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordFieldText;

    @FXML
    private CheckBox showPasswordCheckBox;

    // Method to change the scene based on the provided FXML file and title
    @Override
    public void changeScene(ActionEvent event, String fxmlFile, String title) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource(fxmlFile));

        // Load different FXML files based on the provided file name
        if (fxmlFile.equals("Dashboard.fxml")){
            try {
                root = loader.load();
                DashboardController controller = loader.getController();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }  else {
            try {
                root = loader.load();
                SignupController controller = loader.getController();
            }
            catch(IOException e){
                System.out.println("IOException");
            }
        }

        // Set up the stage with the new scene and display it
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 1000, 800));
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.show();
    }

    // Method to handle login action
    public void login(ActionEvent buttonClick) {
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String visiblePassword = passwordFieldText.getText();

        if (!validateInput(email, password)) {
            showMessage("Please enter both email and password");
            return;
        }

        try {
            boolean loginSuccessful = false;
            if (showPasswordCheckBox.isSelected()) {
                loginSuccessful = DBUtility.verifyLogin(email, visiblePassword);
            } else {
                loginSuccessful = DBUtility.verifyLogin(email, password);
            }

            if (loginSuccessful) {
                // Change scene if login is successful
                changeScene(buttonClick, "Dashboard.fxml", "Dashboard");
                clearFields();
            } else {
                // Show error message if login fails
                showMessage("Incorrect Username or Password");
            }
        } catch (Exception e) {
            showMessage("An error occurred while processing your request. Please try again later");
            // Log the exception for further investigation if needed
            e.printStackTrace();
        }
    }

    // Method to validate user input for login
    private boolean validateInput(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }

    // Method to clear input fields
    private void clearFields() {
        emailField.clear();
        passwordField.clear();
        passwordFieldText.clear();
    }

    // Helper method to display a message dialog with the given message
    private void showMessage(String message) {
        //JOptionPane.showMessageDialog(null, message, "Message!", JOptionPane.INFORMATION_MESSAGE);
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to handle password visibility
    public void showPassword(ActionEvent event) {
        String hiddenPassword = passwordField.getText();
        String showPassword = passwordFieldText.getText();

        if (showPasswordCheckBox.isSelected()) {
            passwordFieldText.setText(hiddenPassword);
            passwordFieldText.setVisible(true);
            passwordField.setVisible(false);
        } else {
            passwordField.setText(showPassword);
            passwordField.setVisible(true);
            passwordFieldText.setVisible(false);
        }
    }

    // Method to navigate to the signup screen
    public void signup(ActionEvent buttonClick){
        changeScene(buttonClick, "Signup.fxml", "Signup");
        // Should set DBUtils.currentLoggedInUser to null
    }
}
