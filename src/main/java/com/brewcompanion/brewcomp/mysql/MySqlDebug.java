package com.brewcompanion.brewcomp.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import com.brewcompanion.brewcomp.Main;

public class MySqlDebug {
    public static void resetAll(){
        //drop all tables
        try(Connection conn = MySql.getDataSource().getConnection()) {
            conn.createStatement().execute("DROP TABLE IF EXISTS Users");
            conn.createStatement().execute("DROP TABLE IF EXISTS Recipes");
        } catch (SQLException e) {
            Main.getLogger().error("Failed to drop tables.");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
