package com.brewcompanion.brewcomp.api.auth;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.brewcompanion.brewcomp.controllers.api.AuthApiController;
import com.brewcompanion.brewcomp.objects.api.auth.LoginPostRequest;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(AuthApiController.class)
class SpringAuthTests {

    @Autowired MockMvc mockMvc;

    @BeforeAll
    static void setupMySQL() {
        // setup MySQL
        MysqlAuthTests.setupMysql();
    }

    @BeforeAll
    static void setupRedis() {
        // setup Redis
        RedisAuthTests.setupRedis();
    }

    @Test
    void testLogin() throws Exception{
        //create a post request
        LoginPostRequest postRequest = new LoginPostRequest();

        //set the username and password
        postRequest.setUsername("test");
        postRequest.setPassword("testpassword");

        //send the post request and check with JUnit
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(postRequest)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    
    
}
