package com.example.w24_3175_g7_onroadsavior;

public class UserHelperClass {

    private String fullName;
    private String userName;
    private String email;
    private String contactNumber;
    private String password;
    private String userType;
    private String serviceType;

    //constructors

    public UserHelperClass(String fullName, String userName, String email, String contactNumber, String password, String userType, String serviceType) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.password = password;
        this.userType = userType;
        this.serviceType = serviceType;
    }

    public UserHelperClass() {

    }

    //getters setters

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}