 package com.example.group12.model;
public class JobApplication {
    private String Email;
    private String MerchantID;
    private String Name;
    public JobApplication(){
    }
    public JobApplication(String email, String merchantID, String name) {
        Email = email;
        MerchantID = merchantID;
        Name = name;
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
}

