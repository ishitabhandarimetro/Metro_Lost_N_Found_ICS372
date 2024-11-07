/**
 * This class represents a user of the application.
 * It contains fields for user ID, email, and name,
 * along with methods to access and modify these fields.
 */

package com.example.metrolost_n_found;

public class User {

    private int userID; // Unique identifier for the user
    private String email; // Email address of the user
    private String name; // Name of the user

    // Constructor to initialize a User object with user ID, email, and name
    public User(int userID, String email, String name) {
        this.userID = userID;
        this.email = email;
        this.name = name;
    }

    // Getter method to retrieve the email of the user
    public String getEmail() {
        return email;
    }

    // Getter method to retrieve the user ID
    public int getUserID() {
        return userID;
    }

    // Getter method to retrieve the name of the user
    public String getName() {
        return name;
    }

    // Setter method to set the email of the user
    public void setEmail(String email) {
        this.email = email;
    }

    // Setter method to set the user ID
    public void setUserID(int ID) {
        this.userID = ID;
    }

    // Setter method to set the name of the user
    public void setName(String name) {
        this.name = name;
    }
}
