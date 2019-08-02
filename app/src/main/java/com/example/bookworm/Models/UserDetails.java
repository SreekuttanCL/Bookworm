package com.example.bookworm.Models;

public class UserDetails {

    private String userAddress;
    private String userCity;
    private String userProvince;
    private String userPostalCode;

    public UserDetails() {

    }

    public UserDetails(String userAddress, String userCity, String userProvince, String userPostalCode) {
        this.userAddress = userAddress;
        this.userCity = userCity;
        this.userProvince = userProvince;
        this.userPostalCode = userPostalCode;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserCity() {
        return userCity;
    }

    public String getUserProvince() {
        return userProvince;
    }

    public String getUserPostalCode() {
        return userPostalCode;
    }
}
