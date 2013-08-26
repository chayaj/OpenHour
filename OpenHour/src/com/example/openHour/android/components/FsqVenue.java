package com.example.openHour.android.components;

import android.location.Location;

/**
 * Foursquare Venue Attribute
 */
public class FsqVenue {
	private String id;
    private String name;
    private String address;
    public String url;
    public String phone;

    public Location location;
    private int distance;

    /**
     * Constructor
     */
    public FsqVenue() {}

    public FsqVenue(String id, String name, String address,
                    String url, String phone,
                    int distance) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.url = url;
        this.phone = phone;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getUrl() {
        return name;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String gePhone() {
        return phone;
    }
    public void sePhone(String phone) {
        this.phone = phone;
    }

    public int getDistance() {
        return distance;
    }
    public void setDistance(int distance) {
        this.distance = distance;
    }
}