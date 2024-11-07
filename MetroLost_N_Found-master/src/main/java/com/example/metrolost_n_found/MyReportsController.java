/**
 * This class serves as the controller for the MyReports screen.
 * It extends the LoggedInController class, inheriting methods for scene navigation and logout functionality.
 * The class initializes the user's profile label and populates the list view with the user's reports.
 * It provides functionality to navigate to the dashboard, delete a selected report, and handles error messages.
 * It utilizes JavaFX for UI components and scene management and JOptionPane for displaying messages.
 */

package com.example.metrolost_n_found;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MyReportsController extends LoggedInController {

    @FXML
    private Label profileLabel;

    @FXML
    private ListView<Report> myReportsListView;

    @FXML
    private ArrayList<Report> observableMyReports = new ArrayList<>();

    // Method to initialize the MyReports screen
    public void initialize() {
        // Populate list view with user's reports and set user's profile label
        observableMyReports = DBUtility.populateMyReportsList();
        myReportsListView.setItems(FXCollections.observableList(observableMyReports));
        profileLabel.setText(DBUtility.CurrentSignedInUser.getName());
    }

    // Method to delete a report
    public void deleteReport(ActionEvent buttonClick) {
        Report reportToDelete = myReportsListView.getSelectionModel().getSelectedItem();

        if (reportToDelete == null) {
            showMessage("Please select a report to delete");
            return;
        }

        try {
            // Remove report from the observable list
            observableMyReports.remove(reportToDelete);
            myReportsListView.setItems(FXCollections.observableArrayList(observableMyReports));

            // Attempt to delete the report from the database
            if (DBUtility.deleteReport(reportToDelete.getReportID())) {
                showMessage("Report was removed successfully");
            } else {
                // Add the report back to the observable list if deletion from the database fails
                observableMyReports.add(reportToDelete);
                showMessage("Failed to remove report. Please try again");
            }
        } catch (Exception e) {
            // Add the report back to the observable list if an error occurs
            observableMyReports.add(reportToDelete);
            showMessage("An error occurred while deleting the report. Please try again later");
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
    public void goToDashboard(ActionEvent buttonClick) {
        super.goToDashboard(buttonClick);
    }

}
