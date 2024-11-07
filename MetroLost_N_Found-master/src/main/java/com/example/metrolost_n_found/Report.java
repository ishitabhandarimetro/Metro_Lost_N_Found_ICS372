/**
 * This class represents a report in the MetroLost_n_Found application.
 * It encapsulates information such as report ID, description, category, location, and contact.
 * It provides methods to retrieve and modify the report attributes.
 * Additionally, it overrides the toString method to provide a string representation of the report.
 */

package com.example.metrolost_n_found;

public class Report {

    // Attributes of a report
    private int reportID;
    private String description;
    private String category;
    private String location;
    private String contact;

    // Constructor to initialize a report object
    public Report(int reportID, String description, String category, String location, String contact) {
        this.reportID = reportID;
        this.description = description;
        this.category = category;
        this.location = location;
        this.contact = contact;
    }

    // Getter method for report ID
    public int getReportID() {
        return reportID;
    }

    // Getter method for description
    public String getDescription() {
        return description;
    }

    // Getter method for category
    public String getCategory() {
        return category;
    }

    // Getter method for location
    public String getLocation() {
        return location;
    }

    // Getter method for contact
    public String getContact() {
        return contact;
    }

    // Setter method for report ID
    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    // Setter method for description
    public void setDescription(String description) {
        this.description = description;
    }

    // Setter method for category
    public void setCategory(String category) {
        this.category = category;
    }

    // Setter method for location
    public void setLocation(String location) {
        this.location = location;
    }

    // Setter method for contact
    public void setContact(String contact) {
        this.contact = contact;
    }

    // Override toString method to provide string representation of the report
    @Override
    public String toString() {
        return "Description:  " + description + "\n" +
                "Category:  " + category + "\n" +
                "Location:  " + location + "\n" +
                "Contact:  " + contact + "\n" + "\n";
    }
}
