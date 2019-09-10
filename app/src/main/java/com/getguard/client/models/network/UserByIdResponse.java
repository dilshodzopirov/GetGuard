package com.getguard.client.models.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserByIdResponse {

    @SerializedName("errorMessage")
    @Expose
    private List<String> errorMessage = null;
    @SerializedName("data")
    @Expose
    private Data data;

    public List<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(List<String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phoneNumber")
        @Expose
        private String phoneNumber;
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("roleType")
        @Expose
        private Integer roleType;
        @SerializedName("photo")
        @Expose
        private Photo photo;
        @SerializedName("birthDate")
        @Expose
        private String birthDate;
        @SerializedName("maritalStatus")
        @Expose
        private String maritalStatus;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("citizenship")
        @Expose
        private String citizenship;
        @SerializedName("about")
        @Expose
        private String about;
        @SerializedName("experience")
        @Expose
        private String experience;
        @SerializedName("driverLicense")
        @Expose
        private String driverLicense;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("userRatingCount")
        @Expose
        private Integer userRatingCount;
        @SerializedName("hasPrivateGuardLicense")
        @Expose
        private Boolean hasPrivateGuardLicense;
        @SerializedName("personalCar")
        @Expose
        private Integer personalCar;
        @SerializedName("weapon")
        @Expose
        private Integer weapon;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public Integer getRoleType() {
            return roleType;
        }

        public void setRoleType(Integer roleType) {
            this.roleType = roleType;
        }

        public Photo getPhoto() {
            return photo;
        }

        public void setPhoto(Photo photo) {
            this.photo = photo;
        }

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCitizenship() {
            return citizenship;
        }

        public void setCitizenship(String citizenship) {
            this.citizenship = citizenship;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getDriverLicense() {
            return driverLicense;
        }

        public void setDriverLicense(String driverLicense) {
            this.driverLicense = driverLicense;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public Integer getUserRatingCount() {
            return userRatingCount;
        }

        public void setUserRatingCount(Integer userRatingCount) {
            this.userRatingCount = userRatingCount;
        }

        public Boolean getHasPrivateGuardLicense() {
            return hasPrivateGuardLicense;
        }

        public void setHasPrivateGuardLicense(Boolean hasPrivateGuardLicense) {
            this.hasPrivateGuardLicense = hasPrivateGuardLicense;
        }

        public Integer getPersonalCar() {
            return personalCar;
        }

        public void setPersonalCar(Integer personalCar) {
            this.personalCar = personalCar;
        }

        public Integer getWeapon() {
            return weapon;
        }

        public void setWeapon(Integer weapon) {
            this.weapon = weapon;
        }

    }

    public class Photo {

        @SerializedName("fileName")
        @Expose
        private String fileName;
        @SerializedName("storageName")
        @Expose
        private String storageName;
        @SerializedName("contentType")
        @Expose
        private String contentType;
        @SerializedName("fileSize")
        @Expose
        private Integer fileSize;
        @SerializedName("uploadStatus")
        @Expose
        private Integer uploadStatus;
        @SerializedName("id")
        @Expose
        private String id;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getStorageName() {
            return storageName;
        }

        public void setStorageName(String storageName) {
            this.storageName = storageName;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public Integer getFileSize() {
            return fileSize;
        }

        public void setFileSize(Integer fileSize) {
            this.fileSize = fileSize;
        }

        public Integer getUploadStatus() {
            return uploadStatus;
        }

        public void setUploadStatus(Integer uploadStatus) {
            this.uploadStatus = uploadStatus;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }
}