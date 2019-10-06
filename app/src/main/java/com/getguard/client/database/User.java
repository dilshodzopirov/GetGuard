package com.getguard.client.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity()
public class User {

    @PrimaryKey()
    private long uid;

    private String id;
    private String userName;
    private String email;
    private String phoneNumber;
    private long birthDay;
    private String photoId;
    private String token;
    private int roleType;

    public User() {
    }

    public User(long uid, String userName, String email, long birthDay, String token, int roleType) {
        this.uid = uid;
        this.userName = userName;
        this.email = email;
        this.birthDay = birthDay;
        this.token = token;
        this.roleType = roleType;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

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

    public long getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(long birthDay) {
        this.birthDay = birthDay;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isGuard() {
        return roleType != 1;
    }

}
