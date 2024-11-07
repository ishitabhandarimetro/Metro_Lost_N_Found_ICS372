/**
 * This abstract class serves as a controller for scenes that require the user to be logged in.
 * It implements the SceneChanger interface for changing scenes.
 * The class provides methods for changing the scene to the login screen, logging out, and navigating to the dashboard.
 * It utilizes JavaFX for scene management and handles IOExceptions that may occur during scene loading.
 */

package com.example.metrolost_n_found;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public abstract class LoggedInController implements SceneChanger {

    // Method to change the scene based on the provided FXML file and title
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
            catch (IOException e){
                System.out.println("IOException");
            }
        }
        else{
            try{
                root = loader.load();
                DashboardController controller = loader.getController();
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

    // Method to handle logout action
    public void logout(ActionEvent event){
        changeScene(event,"Login.fxml","Login");
    }

    // Method to navigate to the dashboard
    public void goToDashboard(ActionEvent event){
        changeScene(event,"Dashboard.fxml","Dashboard");
    }
}