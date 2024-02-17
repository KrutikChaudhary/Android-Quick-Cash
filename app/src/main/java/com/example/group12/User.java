package com.example.group12;

import java.io.Serializable;

public class User implements Serializable {

    private String email;
    private String password;
    private String role;

    public User(){

    }

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
