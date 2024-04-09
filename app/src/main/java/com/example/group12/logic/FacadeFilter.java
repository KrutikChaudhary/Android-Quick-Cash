package com.example.group12.logic;

import android.util.Log;

/**
 * This class provides a facade for filtering operations related to distance calculation.
 */
public class FacadeFilter {

    /**
     * Calculates the distance between two geographical coordinates using the Haversine formula.
     * @param lat1 Latitude of the first point
     * @param lng1 Longitude of the first point
     * @param lat2 Latitude of the second point
     * @param lng2 Longitude of the second point
     * @return The distance between the two points in kilometers.
     */
    public double getDist(float lat1, float lng1, float lat2, float lng2){
        // Use only the distance function from the filter job
        FilterJob filter = new FilterJob();
        double dist = filter.getDistance(lat1,lng1,lat2,lng2);
        return dist;
    }

}
