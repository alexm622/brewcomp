package com.brewcompanion.brewcomp.api.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;

import com.brewcompanion.brewcomp.mysql.Constants;
import com.brewcompanion.brewcomp.mysql.DatabaseInitializer;
import com.brewcompanion.brewcomp.mysql.MySql;
import com.brewcompanion.brewcomp.mysql.MySqlAuthHandler;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

class MysqlAuthTests {
    private static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:5.7")
            .withDatabaseName("brewcompanion")
            .withUsername("brewcompanion")
            .withPassword("brewcompanion");

    @BeforeAll
    static void setupMysql() {
        mySQLContainer.start();

        HikariConfig config = new HikariConfig();
        config.setUsername("brewcompanion");
        config.setPassword("brewcompanion");

        config.setJdbcUrl(mySQLContainer.getJdbcUrl());
        config.setMaximumPoolSize(10);

        MySql.setDataSource(new HikariDataSource(config));

        // initialize the database
        DatabaseInitializer.initialize();

        // insert a test user
        try (var conn = MySql.getDataSource().getConnection();
                var pstmt = conn.prepareStatement(Constants.INSERT_USER)) {
            pstmt.setString(1, "test");
            pstmt.setString(2, "098f6bcd4621d373cade4e832627b4f6");
            pstmt.setString(3, "test@test.com");
            pstmt.setString(4, "$2y$10$bmKeK12yrqG5owKUex6unO9vG3SN4mPoQqQuUeK3FlTelh.P37RPe");

            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void teardown() {
        mySQLContainer.stop();
    }

    @Test
    void testVerifyLogin() {
        assertTrue(MySqlAuthHandler.verifyLogin("test", "testpassword"));
    }

    @Test
    void testGetUsername() {
        assertEquals("test", MySqlAuthHandler.getUsername(1));
    }

}