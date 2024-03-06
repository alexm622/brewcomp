package com.brewcompanion.brewcomp.mysql;

import com.brewcompanion.brewcomp.Main;
import com.brewcompanion.brewcomp.utils.Secrets;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MySql {
    private static HikariDataSource dataSource;

    private MySql() {
        // Private constructor to hide the implicit public one
    }

    public static void initialize() {
        HikariConfig config = new HikariConfig();
        config.setUsername(Secrets.getSecret("MYSQL_USER"));
        config.setPassword(Secrets.getSecret("MYSQL_PASSWORD"));

        config.setJdbcUrl(Main.getConfig().getMysqlUrl());
        config.setMaximumPoolSize(Main.getConfig().getMysqlMaxPoolSize());
        
        //set database name

        dataSource = new HikariDataSource(config);
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }

    public static void shutdown() {
        dataSource.close();
    }
}
