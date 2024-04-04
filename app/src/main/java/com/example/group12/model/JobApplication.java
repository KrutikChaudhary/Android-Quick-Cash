package com.example.group12.model;

/**
 * Represents a job application submitted by an employee.
 */
public class JobApplication {
    private String Email;
    private String MerchantID;
    private String Name;
    private String applicationStatus;
    private String employerEmail;

    public JobApplication(){
    }
    //Getters and setters of email, merchantID, and name of employee
    public JobApplication(String email, String merchantID, String name, String status, String employerEmail) {
        Email = email;
        MerchantID = merchantID;
        Name = name;
        applicationStatus = status;
        this.employerEmail = employerEmail;
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
    public String getEmployerEmail() {
        return employerEmail;
    }

    public void setEmployerEmail(String employerEmail) {
        this.employerEmail = employerEmail;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}