package com.example.gd.experiment_three;

import java.io.Serializable;

/**
 * Created by gd on 16/10/15.
 */
public class User implements Serializable {
    private String username;
    private String phone;
    private String kind;
    private String place;

    public User(String username, String phone, String kind, String place) {
        this.username = username;
        this.phone = phone;
        this.kind = kind;
        this.place = place;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

}
