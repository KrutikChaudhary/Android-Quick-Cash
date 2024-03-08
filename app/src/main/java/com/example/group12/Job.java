package com.example.group12;

import java.io.Serializable;

public class Job implements Serializable {

    private String title;
    private String duration;
    private String salary;

    public Job(){
    }

    public String getTitle(){
        return this.title;
    }

    public String getDuration(){
        return this.duration;
    }

    public String getSalary(){
        return this.salary;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
