package com.brewcompanion.brewcomp.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MySql {
    private static HikariDataSource dataSource;

    private MySql() {
        // Private constructor to hide the implicit public one
    }

    public static void initialize() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(Secrets.getSecret("jdbcUrl"));
        config.setUsername(Secrets.getSecret("username"));
        config.setPassword(Secrets.getSecret("password"));
        config.setMaximumPoolSize(Integer.parseInt(Secrets.getSecret("maximumPoolSize")));

        dataSource = new HikariDataSource(config);
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}
