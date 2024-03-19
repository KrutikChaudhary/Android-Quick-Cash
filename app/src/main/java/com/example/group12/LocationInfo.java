package com.example.group12;
/*

AUTHOR: Yash Roushan, Krutik Chaudhary

This file includes a class named LocationInfo that is designed to encapsulate
all the necessary information regarding a geographical location.
 */
public class LocationInfo {
    private double latitude;
    private double longitude;
    private String address;
    private String locality;
    private String countryCode;

    public LocationInfo(double latitude, double longitude, String address, String locality, String countryCode) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.locality = locality;
        this.countryCode = countryCode;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}