package com.example.group12.model.location;
/*
This file includes a class named LocationInfo that is designed to encapsulate
all the necessary information regarding a geographical location.
 */
public class LocationInfo {
    private double latitude; // Latitude of the location
    private double longitude; // Longitude of the location
    private String address; // Street address of the location
    private String locality; // Locality of the location (e.g., city)
    private String countryCode; // Country code of the location

    /**
     * Constructs a new LocationInfo object with the given location details.
     *
     * @param latitude    The latitude of the location.
     * @param longitude   The longitude of the location.
     * @param address     The street address of the location.
     * @param locality    The locality of the location (e.g., city).
     * @param countryCode The country code of the location.
     */
    public LocationInfo(double latitude, double longitude, String address, String locality, String countryCode) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.locality = locality;
        this.countryCode = countryCode;
    }

    // Getter and setter methods for latitude, longitude, address, locality, and countryCode
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