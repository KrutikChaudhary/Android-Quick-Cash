package com.example.group12.model;
import java.io.Serializable;

/**
 * Represents a job.
 */
public class Job implements Serializable {
    private String title;
    private int duration;
    private float salary;

    private String startDate;
    private String urgency;

    private String location;

    private float longitude;
    private float latitude;

    /**
     * Default constructor.
     */
    public Job(){
    }

    /**
     * Parameterized constructor to initialize a job object.
     * @param title The title of the job.
     * @param salary The salary of the job.
     * @param duration The duration of the job.
     * @param startDate The start date of the job.
     * @param location The location of the job.
     * @param urgency The urgency of the job.
     * @param latitude The latitude of the job location.
     * @param longitude The longitude of the job location.
     */
    public Job(String title, float salary, int duration, String startDate, String location, String urgency, float latitude, float longitude){
        this.title = title;
        this.duration = duration;
        this.salary = salary;
        this.startDate = startDate;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getter and setter methods
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

    public String getUrgency() {
        return urgency;
    }

    public float getLongitude(){ return this.longitude;}

    public float getLatitude(){ return this.latitude; }

    public String getLocation(){ return this.location;}

    public void setStartDate(String date){
        this.startDate = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}