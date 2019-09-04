package com.getguard.client.models.network;

public class Register {

    String[] errorMessage;
    RegisterData data;

    public String[] getErrorMessage() {
        return errorMessage;
    }

    public RegisterData getData() {
        return data;
    }

    public static class RegisterData {

        User user;
        String token, message;

        public User getUser() {
            return user;
        }

        public String getToken() {
            return token;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class User {
        String id, userName, email, role, photoId;
        int roleType;

        public String getId() {
            return id;
        }

        public String getUserName() {
            return userName;
        }

        public String getEmail() {
            return email;
        }

        public String getRole() {
            return role;
        }

        public String getPhotoId() {
            return photoId;
        }

        public int getRoleType() {
            return roleType;
        }
    }

}
