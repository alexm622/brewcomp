package com.brewcompanion.brewcomp.objects.api.auth;

import java.io.Serializable;

import lombok.Data;

@Data
public class LogoutPostRequest implements Serializable{
    //logout stuff

    String sessionToken;

    //some telemetry
    String ip;
    String userAgent;
    String location;
    String device;
    String os;
    
}
