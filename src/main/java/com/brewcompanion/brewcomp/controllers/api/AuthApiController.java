package com.brewcompanion.brewcomp.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brewcompanion.brewcomp.Main;
import com.brewcompanion.brewcomp.mysql.MySqlAuthHandler;
import com.brewcompanion.brewcomp.objects.api.auth.LoginPostRequest;
import com.brewcompanion.brewcomp.redis.TokenManager;

import jakarta.servlet.http.HttpServletRequest;

import com.brewcompanion.brewcomp.objects.api.auth.CreateUserPostRequest;
import com.brewcompanion.brewcomp.objects.api.auth.LoginConfirm;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping(value = {"/api/auth"})
public class AuthApiController {

@PostMapping("/login")
public ResponseEntity<LoginConfirm> postLogin(@RequestBody LoginPostRequest entity, HttpServletRequest request) {
    //get the IP address
    String ip = request.getRemoteAddr();
    entity.setIp(ip);
    
    //right now just print the request
    Main.getLogger().debug(entity.toString());

    //issue a token
    String token = TokenManager.generateToken(MySqlAuthHandler.getUserId(entity.getUsername()), ip);
    
    //create the response
    LoginConfirm loginConfirm = new LoginConfirm();
    loginConfirm.setToken(token);

    return ResponseEntity.ok(loginConfirm);
} 

    @PostMapping("/logout")
    public ResponseEntity<CreateUserPostRequest> postLogout(@RequestBody CreateUserPostRequest entity) {

        //right now just print the request
        Main.getLogger().info(entity.toString());
        
        return ResponseEntity.ok(entity);
        
    }

    @PostMapping("/createuser")
    public String postCreateUser(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }

    @PostMapping("/updateuser")
    public String postUpdateUser(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    
    


	
}
