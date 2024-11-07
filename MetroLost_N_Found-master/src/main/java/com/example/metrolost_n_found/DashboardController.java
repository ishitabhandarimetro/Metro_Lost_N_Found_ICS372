/**
 * This class serves as the controller for the Dashboard screen.
 * It extends LoggedInController to inherit common functionalities.
 * The class handles initialization of the dashboard, scene navigation,
 * and logout functionality. It also sets up event handlers for
 * navigating to other screens.
 */

package com.example.metrolost_n_found;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardController extends LoggedInController{

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label profileLabel;

    // Method to initialize the dashboard
    public void initialize(){
        welcomeLabel.setText("Welcome back " + DBUtility.CurrentSignedInUser.getName());
        profileLabel.setText(DBUtility.CurrentSignedInUser.getName());
    }

    // Method to change scene based on user action
    @Override
    public void changeScene(ActionEvent event, String fxmlFile, String title) {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource(fxmlFile));
        // Load different FXML files based on the provided file name
        if(fxmlFile.equals("Login.fxml")){
            try{
                root = loader.load();
                LoginController controller = loader.getController();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else if(fxmlFile.equals("CreateReport.fxml")){
            try{
                root = loader.load();
                CreateReportController controller = loader.getController();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        } else if(fxmlFile.equals("Overview.fxml"))   {
                try{
                    root = loader.load();
                    OverviewController controller = loader.getController();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            } else{
                try{
                    root = loader.load();
                   MyReportsController  controller = loader.getController();
                }
                catch(IOException e){
                    e.printStackTrace();
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

    // Method to handle logout action
    public void logout(ActionEvent buttonClick){
        changeScene(buttonClick, "Login.fxml", "Login");
        //Should set DBUtils.currentLoggedInUser to null
    }

    // Method to navigate to create report screen
    public void goToCreateReport(ActionEvent buttonClick){
        changeScene(buttonClick, "CreateReport.fxml","Create a Report");
    }

    // Method to navigate to overview screen
    public void goToOverview(ActionEvent buttonClick){
        changeScene(buttonClick,"Overview.fxml", "Overview of Reports");
    }

    // Method to navigate to my reports screen
    public void goToMyReports(ActionEvent buttonClick){
        changeScene(buttonClick, "MyReports.fxml", "My Reports");
    }


}
