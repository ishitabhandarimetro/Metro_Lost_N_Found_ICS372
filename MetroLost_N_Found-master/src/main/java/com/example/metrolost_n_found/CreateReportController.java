/**
 * This class serves as the controller for the Create Report screen.
 * It extends LoggedInController to inherit common functionalities
 * and implements Initializable for initializing the screen.
 * The class handles storing a new report, input validation,
 * and navigation to the dashboard screen.
 */

package com.example.metrolost_n_found;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CreateReportController extends LoggedInController implements Initializable {

    @FXML
    private ComboBox<String> myComboBox;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField locationText;

    @FXML
    private TextField phoneText;

    @FXML
    private Label profileLabel;

    private String[] choices = {"Clothing", "Wallet/ID", "Electronics", "Other"};

    // Method to initialize the screen
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myComboBox.getItems().addAll(choices);
        profileLabel.setText(DBUtility.CurrentSignedInUser.getName());
    }

    // Method to store a new report
    public void storeReport(ActionEvent buttonClick) {
        String description = descriptionText.getText().trim();
        String location = locationText.getText().trim();
        String phone = phoneText.getText().trim();
        String category = myComboBox.getValue();

        // Input validation
        if (description.isEmpty() || location.isEmpty() || phone.isEmpty() || category == null) {
            showMessage("Please fill out all fields and select a category");
            return;
        }

        try {
            // Attempt to store the report in the database
            if (DBUtility.addReport(description, category, location, phone)) {
                showMessage("Report has been created successfully");
                // Clear input fields after successful report creation
                descriptionText.clear();
                locationText.clear();
                phoneText.clear();
                myComboBox.setValue(null);
            } else {
                showMessage("Failed to create the report. Please try again");
            }
        } catch (Exception e) {
            showMessage("An error occurred while creating the report. Please try again later.");
            // Log the exception for further investigation if needed
            e.printStackTrace();
        }
    }

    // Helper method to display a message dialog with the given message
    private void showMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to navigate to the dashboard screen
    public void goToDashboard(ActionEvent buttonClick){
        super.goToDashboard(buttonClick);
    }

}
