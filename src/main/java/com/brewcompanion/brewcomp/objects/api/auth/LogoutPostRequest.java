package com.brewcompanion.brewcomp.objects.api.auth;

import java.io.Serializable;

import lombok.Data;

@Data
public class LogoutPostRequest implements Serializable{
    //logout stuff

    // TODO this should be fixed you can't logout a different user without auth
    String sessionToken;
    String username;

    //some telemetry
    String ip;
    String userAgent;
    String location;
    String device;
    String os;
    
}
