package com.example.group12.logic;

import android.content.Context;
import android.location.Geocoder;

import com.example.group12.core.Constants;

public class FilterJob {

    public FilterJob(){
    }



    public boolean containsParameters(String param, String title){
        return (title.toLowerCase()).contains(param.toLowerCase());
    }

    public boolean containsSalary(String param, float num){

        if(param.equals("")){
            return true;
        }

        boolean results = false;


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

    public boolean containsDuration(String param, int duration){

        if(param.equals("")) return true;

        boolean result = false;

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

    public boolean inDistance(String param, float usr_lat, float usr_lng, float job_lat, float job_lng){
        if(param.equals("")){
            return true;
        }

        float distance = getDistance(usr_lat, usr_lng, job_lat, job_lng);

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

        // if distiance is in param return true
        return false;
    }


    // return euclidean distance
    protected float getDistance(float lat1, float lng1, float lat2, float lng2){
        // convert degree to km
        float km_lat = (float) ((lat1 - lat2)/110.574);
        float km_lng = (float) ((lng1 - lng2)/Math.cos((lat1 - lat2) * Math.PI / 180));

        // eaclidean distance formula
        float km_x = km_lng*km_lng;
        float km_y = km_lat*km_lat;

        return (float) Math.sqrt(km_x + km_y);
    }




}
