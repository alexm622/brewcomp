package com.brewcompanion.brewcomp.objects.api.auth;

import java.io.Serializable;

import lombok.Data;

@Data
public class LoginPostRequest implements Serializable{
    //login essentials
    String username;
    String password;

    //some telemetry
    
    String ip; //this will have to be recorded on our end
    String userAgent;
    String platform;
    String language;
    String location;
    String device;
    String os;
    
}
