package com.brewcompanion.brewcomp.objects.database;

import lombok.Data;

@Data
public class User {
    private final String username;
    private final String hash;
    private final String email;
    private final String salt;

    public User(String username, String hash, String email, String salt) {
        this.username = username;
        this.hash = hash;
        this.email = email;
        this.salt = salt;
    }
    
}
