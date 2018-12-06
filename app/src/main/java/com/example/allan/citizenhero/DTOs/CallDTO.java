package com.example.allan.citizenhero.DTOs;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CallDTO implements Serializable {

    @SerializedName("user_id")
    private Long userId;

    @SerializedName("call_type")
    private int callType;

    @SerializedName("street")
    private String street;

    @SerializedName("neighborhood")
    private String neighborhood;

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String state;

    @SerializedName("complement")
    private String complement;

    @SerializedName("observation")
    private String observation;

    @SerializedName("lat")
    private Double latitude;

    @SerializedName("lng")
    private Double longitude;

    public CallDTO() {
    }

    public String getFullAddress(){
        return this.street + ", " + this.neighborhood + ", " + this.city + " - " + this.state;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
