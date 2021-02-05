package com.Carleasing.carleasing.model;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

@Data
public class User implements Serializable {

    public String username;
    public String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
   // Hashtable<User, String> table = new Hashtable<User, String>();
   HashMap<User, String> mapList = new HashMap<User, String>();
    User user = new User("user", "password");
    User admin = new User("admin", "adminPassword");



}


