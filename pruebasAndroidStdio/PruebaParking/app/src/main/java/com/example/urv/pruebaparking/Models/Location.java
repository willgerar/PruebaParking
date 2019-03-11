package com.example.urv.pruebaparking.Models;

public class Location {

    private int id;
    private String city;
    private  float latitude;
    private float longitude;
    private int postal_code;
    private String state_province;
    private String street_address;

    public Location(int id, String city, float latitude, float longitude, int postal_code, String state_province, String state_address){
        this.id = id;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.postal_code = postal_code;
        this.state_province = state_province;
        this.street_address = state_address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(int postal_code) {
        this.postal_code = postal_code;
    }

    public String getState_province() {
        return state_province;
    }

    public void setState_province(String state_province) {
        this.state_province = state_province;
    }

    public String getStreet_address() {
        return street_address;
    }

    public void setState_address(String state_address) {
        this.street_address = state_address;
    }


    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", postal_code=" + postal_code +
                ", state_province='" + state_province + '\'' +
                ", state_address='" + street_address + '\'' +
                '}';
    }
}
