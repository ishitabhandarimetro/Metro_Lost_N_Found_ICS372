/**
 * This class serves as the controller for the Signup screen.
 * It implements the SceneChanger interface to facilitate scene navigation.
 * The class handles user sign-up process, input validation, and UI events such as password visibility.
 * It utilizes JavaFX for UI components and scene management.
 */

package com.example.metrolost_n_found;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class SignupController implements SceneChanger{

    @FXML
    private CheckBox showPasswordCheckBox;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField1;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField passwordFieldText;

    @FXML
    private TextField passwordFieldText1;

    @FXML
    private TextField nameField;


    // Method to change the scene
    @Override
    public void changeScene(ActionEvent event, String fxmlFile, String title) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(SignupController.class.getResource(fxmlFile));

        try{
            root = loader.load();
            LoginController controller = loader.getController();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 1000, 800));
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.show();
    }

    // Method to toggle password visibility
    public void showPassword(ActionEvent event) {
        String hiddenPassword = passwordField.getText();
        String hiddenPassword1 = passwordField1.getText();
        String showPassword = passwordFieldText.getText();
        String showPassword1 = passwordFieldText1.getText();

        if (showPasswordCheckBox.isSelected()) {
            passwordFieldText.setText(hiddenPassword);
            passwordFieldText1.setText(hiddenPassword1);
            passwordFieldText.setVisible(true);
            passwordFieldText1.setVisible(true);
            passwordField.setVisible(false);
            passwordField1.setVisible(false);
        } else {
            passwordField.setText(showPassword);
            passwordField1.setText(showPassword1);
            passwordField.setVisible(true);
            passwordField1.setVisible(true);
            passwordFieldText.setVisible(false);
            passwordFieldText1.setVisible(false);
        }
    }

    // Method to handle logout action
    public void logout(ActionEvent event){
        changeScene(event,"Login.fxml","Login");
        //Should set the DBUtils.currentLoggedInUser to null

    }

    //This method handles the sign-up process when a user clicks on the sign-up button.
    //It performs input validation, checks for duplicate emails, and signs up the user if all conditions are met.
    public void signup(ActionEvent buttonClick) {
        // Retrieve input values
        String email = emailField.getText().trim();
        String name = nameField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPassword = passwordField1.getText().trim();

        // Constants
        final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@go\\.minnstate\\.edu$";
        final String SPECIAL_CHARACTERS = "(){}[]|`¬¦! £$%^&*<>:;#~_-+=,@.?/";
        final int MIN_PASSWORD_LENGTH = 6;

        // Input validation
        if (anyEmpty(email, name, password, confirmPassword)) {
            showMessage("Please enter all fields");
            return;
        }

        // Check if email matches the pattern
        if (!email.matches(EMAIL_REGEX)) {
            showMessage("Failed to sign up. Please use a minnstate edu email address");
            return;
        }

        // Validate for duplicate email
        String duplicateEmail = DBUtility.findDuplicateEmail(email);
        if (duplicateEmail != null) {
            showMessage("Failed to sign up. Email '" + duplicateEmail + "' is already taken. Please try using a different email.");
            return;
        }

        // Check if password contains special characters
        if (containsSpecialCharacters(password, SPECIAL_CHARACTERS)) {
            showMessage("Password contains special characters: " + SPECIAL_CHARACTERS);
            return;
        }

        // Validate password length
        if (password.length() < MIN_PASSWORD_LENGTH) {
            showMessage("Password must be more than " + MIN_PASSWORD_LENGTH + " characters");
            return;
        }

        // Validate if showPasswordCheckBox is selected
        if (showPasswordCheckBox.isSelected()) {
            showMessage("Please uncheck the show password checkbox to proceed");
            return;
        }

        // Check if passwords match after trimming leading and trailing whitespace
        if (!password.trim().equals(confirmPassword.trim())) {
            showMessage("Failed to sign up. Passwords do not match.");
            return;
        }

        try {
            // Sign up user
            DBUtility.signUp(email, name, password);
            showMessage("Successfully registered your new account");
            // Clear input fields after successful registration
            clearInputFields();
        } catch (Exception e) {
            showMessage("Failed to sign up. Please try again later.");
            // Log the exception for further investigation if needed
            e.printStackTrace();
        }
    }


    // Helper method to check if any of the given strings is empty
    private boolean anyEmpty(String... strings) {
        for (String str : strings) {
            if (str.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    // Helper method to check if the given string contains any special characters
    private boolean containsSpecialCharacters(String str, String specialChars) {
        for (char ch : str.toCharArray()) {
            if (specialChars.indexOf(ch) != -1) {
                return true;
            }
        }
        return false;
    }

    // Helper method to display a message dialog with the given message
    private void showMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }


    // Helper method to clear input fields
    private void clearInputFields() {
        emailField.clear();
        nameField.clear();
        passwordField.clear();
        passwordField1.clear();
        passwordFieldText.clear();
        passwordFieldText1.clear();
    }
}
