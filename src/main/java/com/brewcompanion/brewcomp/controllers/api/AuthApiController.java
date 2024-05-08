package com.brewcompanion.brewcomp.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brewcompanion.brewcomp.Main;
import com.brewcompanion.brewcomp.auth.TokenManager;
import com.brewcompanion.brewcomp.mysql.auth.MySqlAuthHandler;
import com.brewcompanion.brewcomp.objects.api.auth.LoginPostRequest;
import com.brewcompanion.brewcomp.objects.api.auth.LogoutPostRequest;
import com.brewcompanion.brewcomp.objects.api.auth.UpdateUserPostRequest;

import jakarta.servlet.http.HttpServletRequest;

import com.brewcompanion.brewcomp.objects.api.auth.CreateUserPostRequest;
import com.brewcompanion.brewcomp.objects.api.auth.LoginConfirm;

import org.springframework.web.bind.annotation.CookieValue;
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

    //verify the login
    if (!MySqlAuthHandler.verifyLogin(entity.getUsername(), entity.getPassword())) {
        return ResponseEntity.status(401).build();
    }


    //issue a token
    String token = TokenManager.generateToken(MySqlAuthHandler.getUserId(entity.getUsername()), ip);
    
    //create the response
    LoginConfirm loginConfirm = new LoginConfirm();
    loginConfirm.setToken(token);

    return ResponseEntity.ok(loginConfirm);
} 

    @PostMapping("/logout")
    public ResponseEntity<Boolean> postLogout(@RequestBody LogoutPostRequest entity, @CookieValue(value = "token") String token) {
        //right now just print the request
        Main.getLogger().info(entity.toString());

        
        //delete the token
        TokenManager.deleteToken(token, MySqlAuthHandler.getUserId(entity.getUsername()));
        
        return ResponseEntity.ok(true);
        
    }

    @PostMapping("/createuser")
    public ResponseEntity<Boolean> postCreateUser(@RequestBody CreateUserPostRequest entity) {

        boolean exists = MySqlAuthHandler.doesUsernameExist(entity.getUsername());

        if (exists) {
            return ResponseEntity.status(409).build();
        }
        
        MySqlAuthHandler.insertUser(entity.getUsername(), entity.getEmail(), entity.getPassword());

        return ResponseEntity.ok(true);
    }

    @PostMapping("/updateuser")
    public ResponseEntity<Boolean> postUpdateUser(@RequestBody UpdateUserPostRequest entity) {
        
        
        return ResponseEntity.ok(true);
    }
    
    


	
}
