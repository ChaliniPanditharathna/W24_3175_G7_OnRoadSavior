package com.example.w24_3175_g7_onroadsavior.Model;

public class RequestDetails {
    private String breakDownType;
    private String location;
    private String description;
    private String createdDate;
    private String updateDate;
    private String image;
    private int userId;
    private int providerId;

    private String userName;

    private String phoneNo;

    public RequestDetails(){}
    public RequestDetails(String breakDownType, String location, String description, String createdDate, String updateDate, String image, int userId, int providerId, String userName, String phoneNo) {
        this.breakDownType = breakDownType;
        this.location = location;
        this.description = description;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.image = image;
        this.userId = userId;
        this.providerId = providerId;
        this.userName = userName;
        this.phoneNo = phoneNo;
    }

    public String getBreakDownType() {
        return breakDownType;
    }

    public void setBreakDownType(String breakDownType) {
        this.breakDownType = breakDownType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
