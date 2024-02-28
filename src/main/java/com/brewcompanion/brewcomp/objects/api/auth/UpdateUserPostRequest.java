package com.brewcompanion.brewcomp.objects.api.auth;

import java.io.Serializable;

import lombok.Data;

@Data
public class UpdateUserPostRequest implements Serializable{
    //login essentials
    String username;
    String password;

    //user data
    String email;

    //some telemetry
    
    String ip;
    String userAgent;
    String location;
    String device;
    String os;
    
}
