package com.example.group12.model;

/**
 * Represents a job application submitted by an employee.
 */
public class JobApplication {
    private String Email;
    private String MerchantID;
    private String Name;
    private String applicationStatus;
    public JobApplication(){
    }
    //Getters and setters of email, merchantID, and name of employee
    public JobApplication(String email, String merchantID, String name, String status) {
        Email = email;
        MerchantID = merchantID;
        Name = name;
        applicationStatus = status;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public String getMerchantID() {
        return MerchantID;
    }
    public void setMerchantID(String merchantID) {
        MerchantID = merchantID;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}