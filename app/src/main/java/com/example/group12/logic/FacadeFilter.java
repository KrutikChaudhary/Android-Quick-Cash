package com.example.group12.logic;

import android.util.Log;

/*
FACADE STRUCTURAL DESIGN PATTERN
 */
public class FacadeFilter {


    public float getDist(float lat1, float lng1, float lat2, float lng2){
        FilterJob filter = new FilterJob();
        float dist = filter.getDistance(lat1,lng1,lat2,lng2);


        return dist;
    }

}
