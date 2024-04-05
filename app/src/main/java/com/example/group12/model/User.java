package com.example.group12.model;

import java.io.Serializable;

/**
 * Abstract class representing a user of the application.
 * This class provides basic attributes and methods common to all types of users.
 */
public abstract class User {

    private String email; // The email address of the user
    private String password; // The password of the user
    private String role; // The role of the user (e.g., employee, employer)

    // Default Constructor
    public User(){
    }

    // Getters and Setters
    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public String getRole(){
        return this.role;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setRole(String role){
        this.role = role;
    }


}
