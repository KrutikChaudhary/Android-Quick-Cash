package com.example.group12.logic;


import android.content.Context;
import android.location.Geocoder;
import android.util.Log;

import com.example.group12.core.Constants;

/**
 * The FilterJob class provides methods to filter job listings based on various criteria.
 */
public class FilterJob {

    /**
     * Default constructor for the FilterJob class.
     */
    public FilterJob(){
    }

    /**
     * Checks if the job title contains the specified parameter.
     *
     * @param param The parameter to be checked.
     * @param title The title of the job.
     * @return true if the job title contains the parameter, false otherwise.
     */
    public boolean containsParameters(String param, String title){
        return (title.toLowerCase()).contains(param.toLowerCase());
    }

    /**
     * Checks if the job salary is within the specified range.
     *
     * @param param The selected salary range.
     * @param num The salary of the job.
     * @return true if the job salary is within the specified range, false otherwise.
     */
    public boolean containsSalary(String param, float num){
        // Check if the salary parameter is empty
        if(param.equals(Constants.SPINNER_SALARY_SELECT)){
            return true;
        }

        boolean results = false;

        // Compare the job salary with each salary range
        if(param.equals(Constants.SPINNER_SALARY_RANGE_ONE)){
            if(num >= (float) 15) {
                results = true;
            }
        }
        else if(param.equals(Constants.SPINNER_SALARY_RANGE_TWO)){
            if(num >= (float) 20) {
                results = true;
            }
        }
        else if(param.equals(Constants.SPINNER_SALARY_RANGE_THREE)){
            if(num >= (float) 30) {
                results = true;
            }
        }
        else if(param.equals(Constants.SPINNER_SALARY_RANGE_FOUR)){
            if(num >= (float) 40) {
                results = true;
            }
        }
        else if(param.equals(Constants.SPINNER_SALARY_RANGE_FIVE)){
            if(num >= (float) 50) {
                results = true;
            }
        }
        else if(param.equals(Constants.SPINNER_SALARY_RANGE_SIX)){
            if(num >= (float) 100) {
                results = true;
            }
        }

        return results;
    }

    /**
     * Checks if the job duration is within the specified range.
     *
     * @param param The selected duration range.
     * @param duration The duration of the job.
     * @return true if the job duration is within the specified range, false otherwise.
     */
    public boolean containsDuration(String param, int duration){
        // Check if the duration parameter is empty
        if(param.equals("")) return true;

        boolean result = false;

        // Compare the job duration with each duration range
        if(param.equals(Constants.SPINNER_DURATION_RANGE_ONE)){
            if(duration <= 1){
                return true;
            }
        }
        else if(param.equals(Constants.SPINNER_DURATION_RANGE_TWO)){
            if(duration <= 5){
                return true;
            }
        }
        else if(param.equals(Constants.SPINNER_DURATION_RANGE_THREE)){
            if(duration <= 10){
                return true;
            }
        }
        else if(param.equals(Constants.SPINNER_DURATION_RANGE_FOUR)){
            if(duration <= 24){
                return true;
            }
        }
        else if(param.equals(Constants.SPINNER_DURATION_RANGE_FIVE)){
            if(duration <= 40){
                return true;
            }
        }
        else if(param.equals(Constants.SPINNER_DURATION_RANGE_SIX)){
            if(duration > 40){
                return true;
            }
        }
        return result;
    }

    /**
     * Checks if the job location is within the specified distance range from the user's location.
     *
     * @param param The selected distance range.
     * @param usr_lat The latitude of the user's location.
     * @param usr_lng The longitude of the user's location.
     * @param job_lat The latitude of the job location.
     * @param job_lng The longitude of the job location.
     * @return true if the job location is within the specified distance range, false otherwise.
     */
    public boolean inDistance(String param, float usr_lat, float usr_lng, float job_lat, float job_lng){
        if(param.equals("")){
            return true;
        }

        double distance = getDistance(usr_lat, usr_lng, job_lat, job_lng);

        // Compare the distance with each distance range
        if(param.equals(Constants.SPINNER_LOCATION_RANGE_ONE)){
            if(.5 >= distance){
                return true;
            }
        }
        else if(param.equals(Constants.SPINNER_LOCATION_RANGE_TWO)){
            if(1.0 >= distance){
                return true;
            }
        }
        else if(param.equals(Constants.SPINNER_LOCATION_RANGE_THREE)){
            if(2.0 >= distance){
                return true;
            }
        }
        else if(param.equals(Constants.SPINNER_LOCATION_RANGE_FOUR)){
            if(3.0 >= distance){
                return true;
            }
        }
        else if(param.equals(Constants.SPINNER_LOCATION_RANGE_FIVE)){
            if(5.0 >= distance){
                return true;
            }
        }
        else if(param.equals(Constants.SPINNER_LOCATION_RANGE_SIX)){
            if(10.0 >= distance){
                return true;
            }
        }

        // If distance is within the specified range, return true
        return false;
    }

    /**
     * Calculates the Euclidean distance between two points using their latitude and longitude.
     *
     * @param lat1 Latitude of the first point.
     * @param lng1 Longitude of the first point.
     * @param lat2 Latitude of the second point.
     * @param lng2 Longitude of the second point.
     * @return The calculated distance.
     */
    public double getDistance(float lat1, float lng1, float lat2, float lng2){
        // Radius of the earth in kilometers. Use 3956 for miles
        final float R = 6371;

        // Convert latitudes and longitudes from degrees to radians
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lng2 - lng1);

        // Apply the Haversine formula
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calculate the distance
        double distance = R * c;

        return distance;
    }
}
