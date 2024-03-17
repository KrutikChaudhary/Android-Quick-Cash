package com.example.group12.model;
import java.io.Serializable;
public class Job implements Serializable {
    private String title;
    private int duration;
    private float salary; //per hour
    private String startDate;

    private String location;

    public Job(){

    }
    public Job(String title, float salary, int duration, String startDate, String location){
        this.title = title;
        this.duration = duration;
        this.salary = salary;
        this.startDate = startDate;
        this.location = location;
//this.location = location;
    }
    public String getTitle(){
        return this.title;
    }
    public int getDuration(){
        return this.duration;
    }
    public float getSalary(){
        return this.salary;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setSalary(float salary) {
        this.salary = salary;
    }
    public String getStartDate() {
        return this.startDate;
    }

    public String getLocation(){ return this.location;}
    public void setStartDate(String date){
        this.startDate = date;
    }

    public void setLocation(String location){ this.location = location;}
}