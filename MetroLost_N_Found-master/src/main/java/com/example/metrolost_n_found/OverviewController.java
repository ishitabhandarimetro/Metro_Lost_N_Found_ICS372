/**
 * This class serves as the controller for the Overview screen.
 * It extends the LoggedInController class, inheriting methods for scene navigation.
 * The class initializes the user's profile label and populates the list view with all reports or filtered reports based on radio button selection.
 * It provides functionality to apply filters, navigate to the dashboard, and handles UI events.
 * It utilizes JavaFX for UI components and scene management.
 */

package com.example.metrolost_n_found;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import java.util.ArrayList;

public class OverviewController extends LoggedInController {

    @FXML
    private Label profileLabel;

    @FXML
    private RadioButton R1_Clothing;

    @FXML
    private RadioButton R2_Wallet;

    @FXML
    private RadioButton R3_Electronic;

    @FXML
    private RadioButton R4_Other;

    @FXML
    private RadioButton R5_All;

    @FXML
    private ListView<Report> listViewOfReports;

    @FXML
    private ArrayList<Report> observableReports = new ArrayList<>();

    // Method to initialize the Overview screen
    public void initialize() {
        // Populate list view with all reports and set user's profile label
        observableReports = DBUtility.populateAllReportsList();
        profileLabel.setText(DBUtility.CurrentSignedInUser.getName());
        listViewOfReports.setItems(FXCollections.observableArrayList(observableReports));
        R5_All.setSelected(true); // Set R5_All as selected by default
        applyFilter(null); // Apply filter initially
    }

    // Method to apply filter based on radio button selection
    public void applyFilter(ActionEvent radioButtonClick) {
        String filterText = "";

        // Determine the filter text based on the selected radio button
        if (R1_Clothing.isSelected()) {
            filterText = R1_Clothing.getText();
        } else if (R2_Wallet.isSelected()) {
            filterText = R2_Wallet.getText();
        } else if (R3_Electronic.isSelected()) {
            filterText = R3_Electronic.getText();
        } else if (R4_Other.isSelected()) {
            filterText = R4_Other.getText();
        }

        // Populate the list based on the selected filter
        if (R5_All.isSelected()) {
            observableReports = DBUtility.populateAllReportsList();
        } else {
            observableReports = DBUtility.populateFilteredReportsList(filterText);
        }

        // Set the items of the list view with the populated reports
        listViewOfReports.setItems(FXCollections.observableArrayList(observableReports));
    }

    // Method to navigate to the dashboard screen
    public void goToDashboard(ActionEvent buttonClick) {
        super.goToDashboard(buttonClick);
    }
}
