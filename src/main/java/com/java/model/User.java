package com.java.model;

import java.util.HashMap;
import java.util.Map;

public class User {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private Map<Provider, String> meta;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<Provider, String> getMeta() {
        if (this.meta == null) {
            meta = new HashMap<>();
        }
        return meta;
    }

    public void setMeta(Map<Provider, String> meta) {
        this.meta = meta;
    }
}
