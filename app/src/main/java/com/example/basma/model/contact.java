package com.example.basma.model;

import java.io.Serializable;

public class contact implements Serializable {
    private int id;
    private String name;
    private String phone;
    private byte[] imageUrl;
    private String email;
    public contact(int id, String name, String phone, byte[] imageUrl, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.email = email;
    }
    public contact(String name, String phone, byte[] imageUrl , String email) {
this.email = email;
        this.name = name;
        this.phone = phone;
        this.imageUrl = imageUrl;
    }
    public contact() {

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImageUrl(byte[] imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public byte[] getImageUrl() {
        return imageUrl;
    }
}
