package com.berber.berberapp.Models;

import java.util.ArrayList;

/**
 * Created by gabotrugomez on 11/8/17.
 */

public class Vendor {
    private String name;
    private ArrayList<String> zones;
    private String phone;
    private String email;
    private String imgUrl = "";

    public Vendor() {
    }

    public Vendor(String name, ArrayList<String> zones, String phone) {
        this.name = name;
        this.zones = zones;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getZones() {
        return zones;
    }

    public void setZones(ArrayList<String> zones) {
        this.zones = zones;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
