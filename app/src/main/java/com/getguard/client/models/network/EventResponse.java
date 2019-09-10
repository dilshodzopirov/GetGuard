package com.getguard.client.models.network;

import java.util.ArrayList;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventResponse {

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

    public class Creator {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("userRatingCount")
        @Expose
        private Integer userRatingCount;
        @SerializedName("photoId")
        @Expose
        private String photoId;
        @SerializedName("photo")
        @Expose
        private Photo photo;

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

        public String getPhotoId() {
            return photoId;
        }

        public void setPhotoId(String photoId) {
            this.photoId = photoId;
        }

        public Photo getPhoto() {
            return photo;
        }

        public void setPhoto(Photo photo) {
            this.photo = photo;
        }

    }

    public class Data {

        @SerializedName("creator")
        @Expose
        private Creator creator;
        @SerializedName("executor")
        @Expose
        private Executor executor;
        @SerializedName("respondedUsers")
        @Expose
        private List<RespondedUser> respondedUsers = null;
        @SerializedName("eventType")
        @Expose
        private Integer eventType;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("startDate")
        @Expose
        private String startDate;
        @SerializedName("endDate")
        @Expose
        private String endDate;
        @SerializedName("jobStartTime")
        @Expose
        private String jobStartTime;
        @SerializedName("guardJobEndTime")
        @Expose
        private String guardJobEndTime;
        @SerializedName("creatorJobEndTime")
        @Expose
        private String creatorJobEndTime;
        @SerializedName("ratePrice")
        @Expose
        private Integer ratePrice;
        @SerializedName("dressCode")
        @Expose
        private Integer dressCode;
        @SerializedName("hasPrivateGuardLicense")
        @Expose
        private Boolean hasPrivateGuardLicense;
        @SerializedName("photoId")
        @Expose
        private String photoId;
        @SerializedName("photo")
        @Expose
        private Photo___ photo;
        @SerializedName("personalCar")
        @Expose
        private Integer personalCar;
        @SerializedName("weapon")
        @Expose
        private Integer weapon;
        @SerializedName("additionalInformation")
        @Expose
        private String additionalInformation;
        @SerializedName("createDate")
        @Expose
        private String createDate;
        @SerializedName("creatorId")
        @Expose
        private String creatorId;
        @SerializedName("isActive")
        @Expose
        private Boolean isActive;
        @SerializedName("eventStatus")
        @Expose
        private Integer eventStatus;
        @SerializedName("executorId")
        @Expose
        private String executorId;
        @SerializedName("id")
        @Expose
        private String id;

        public Creator getCreator() {
            return creator;
        }

        public void setCreator(Creator creator) {
            this.creator = creator;
        }

        public Executor getExecutor() {
            return executor;
        }

        public void setExecutor(Executor executor) {
            this.executor = executor;
        }

        public List<RespondedUser> getRespondedUsers() {
            return respondedUsers;
        }

        public void setRespondedUsers(List<RespondedUser> respondedUsers) {
            this.respondedUsers = respondedUsers;
        }

        public Integer getEventType() {
            return eventType;
        }

        public void setEventType(Integer eventType) {
            this.eventType = eventType;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getJobStartTime() {
            return jobStartTime;
        }

        public void setJobStartTime(String jobStartTime) {
            this.jobStartTime = jobStartTime;
        }

        public String getGuardJobEndTime() {
            return guardJobEndTime;
        }

        public void setGuardJobEndTime(String guardJobEndTime) {
            this.guardJobEndTime = guardJobEndTime;
        }

        public String getCreatorJobEndTime() {
            return creatorJobEndTime;
        }

        public void setCreatorJobEndTime(String creatorJobEndTime) {
            this.creatorJobEndTime = creatorJobEndTime;
        }

        public Integer getRatePrice() {
            return ratePrice;
        }

        public void setRatePrice(Integer ratePrice) {
            this.ratePrice = ratePrice;
        }

        public Integer getDressCode() {
            return dressCode;
        }

        public void setDressCode(Integer dressCode) {
            this.dressCode = dressCode;
        }

        public Boolean getHasPrivateGuardLicense() {
            return hasPrivateGuardLicense;
        }

        public void setHasPrivateGuardLicense(Boolean hasPrivateGuardLicense) {
            this.hasPrivateGuardLicense = hasPrivateGuardLicense;
        }

        public String getPhotoId() {
            return photoId;
        }

        public void setPhotoId(String photoId) {
            this.photoId = photoId;
        }

        public Photo___ getPhoto() {
            return photo;
        }

        public void setPhoto(Photo___ photo) {
            this.photo = photo;
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

        public String getAdditionalInformation() {
            return additionalInformation;
        }

        public void setAdditionalInformation(String additionalInformation) {
            this.additionalInformation = additionalInformation;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(String creatorId) {
            this.creatorId = creatorId;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public Integer getEventStatus() {
            return eventStatus;
        }

        public void setEventStatus(Integer eventStatus) {
            this.eventStatus = eventStatus;
        }

        public String getExecutorId() {
            return executorId;
        }

        public void setExecutorId(String executorId) {
            this.executorId = executorId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

    public class Executor {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("userRatingCount")
        @Expose
        private Integer userRatingCount;
        @SerializedName("photoId")
        @Expose
        private String photoId;
        @SerializedName("photo")
        @Expose
        private Photo_ photo;

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

        public String getPhotoId() {
            return photoId;
        }

        public void setPhotoId(String photoId) {
            this.photoId = photoId;
        }

        public Photo_ getPhoto() {
            return photo;
        }

        public void setPhoto(Photo_ photo) {
            this.photo = photo;
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

    public class Photo_ {

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

    public class Photo__ {

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

    public class Photo___ {

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

    public class RespondedUser {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("userRatingCount")
        @Expose
        private Integer userRatingCount;
        @SerializedName("photoId")
        @Expose
        private String photoId;
        @SerializedName("photo")
        @Expose
        private Photo__ photo;

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

        public String getPhotoId() {
            return photoId;
        }

        public void setPhotoId(String photoId) {
            this.photoId = photoId;
        }

        public Photo__ getPhoto() {
            return photo;
        }

        public void setPhoto(Photo__ photo) {
            this.photo = photo;
        }

    }
}
