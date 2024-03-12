package com.example.group12.model;
import java.io.Serializable;
public class Job implements Serializable {
    private String title;
    private String duration;
    private String salary; //per hour
    //private LatLng location;
    private String startDate;
    private String urgency;
    public Job(String title, String salary, String duration, String startDate){
        this.title = title;
        this.duration = duration;
        this.salary = salary;
        this.startDate = startDate;
//this.location = location;
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
    public String getStartDate() {
        return this.startDate;
    }
    public String getUrgency(){
        return this.urgency;
    }
    public void setStartDate(String date){
        this.startDate = date;
    }
    public void setUrgency(String urgency){
        this.urgency = urgency;
    }
}