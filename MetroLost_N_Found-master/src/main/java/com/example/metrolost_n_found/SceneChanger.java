/**
 * This interface defines a contract for changing scenes in the MetroLost_n_Found application.
 * It provides a method to change the scene, taking an ActionEvent, the FXML file name, and the title of the scene as parameters.
 */

package com.example.metrolost_n_found;

import javafx.event.ActionEvent;

public interface SceneChanger {
    // Method signature to change the scene
    void changeScene(ActionEvent event, String fxmlFile, String title);
}
