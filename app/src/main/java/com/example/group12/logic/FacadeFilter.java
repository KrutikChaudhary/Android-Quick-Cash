package com.example.group12.logic;
/*
FACADE STRUCTURAL DESIGN PATTERN
 */
public class FacadeFilter {


    public float getDist(float lat1, float lng1, float lat2, float lng2){
        FilterJob filter = new FilterJob();

        return filter.getDistance(lat1,lng1,lat2,lng2);
    }

}
