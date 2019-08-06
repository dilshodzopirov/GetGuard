package com.getguard.client.models.network;

import java.util.ArrayList;

public class EventResponse {
    String errorMessage;
    Data data;

    public String getErrorMessage() {
        return errorMessage;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        User creator;
        User executor;
        ArrayList<User> repondedUsers;
        int eventType, dressCode, personalCar, weapon, eventStatus;
        String city, address, startDate, endDate, additionalInformation, creatorId, executorId, id;
        double ratePrice;
        boolean hasPrivateGuardLicense;

        public User getCreator() {
            return creator;
        }

        public User getExecutor() {
            return executor;
        }

        public ArrayList<User> getRepondedUsers() {
            return repondedUsers;
        }

        public int getEventType() {
            return eventType;
        }

        public int getDressCode() {
            return dressCode;
        }

        public int getPersonalCar() {
            return personalCar;
        }

        public int getWeapon() {
            return weapon;
        }

        public int getEventStatus() {
            return eventStatus;
        }

        public String getCity() {
            return city;
        }

        public String getAddress() {
            return address;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public String getAdditionalInformation() {
            return additionalInformation;
        }

        public String getCreatorId() {
            return creatorId;
        }

        public String getExecutorId() {
            return executorId;
        }

        public String getId() {
            return id;
        }

        public double getRatePrice() {
            return ratePrice;
        }

        public boolean isHasPrivateGuardLicense() {
            return hasPrivateGuardLicense;
        }
    }

    public static class User {
        String id, userName, photoId, photo;
        float rating;
        int userRatingCount;

        public String getId() {
            return id;
        }

        public String getUserName() {
            return userName;
        }

        public String getPhotoId() {
            return photoId;
        }

        public String getPhoto() {
            return photo;
        }

        public float getRating() {
            return rating;
        }

        public int getUserRatingCount() {
            return userRatingCount;
        }
    }
}
