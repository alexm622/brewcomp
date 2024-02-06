package com.brewcompanion.brewcomp.utils.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.brewcompanion.brewcomp.Main;

public class DatabaseInitializer {
    public static void initialize() {
        tables();
    }

    private static void tables() {
        try {
            List<TableEnum> tables = checkTables();

            createTables(tables);
        } catch (Exception e) {
            Main.getLogger().error("Failed to create tables.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static List<TableEnum> checkTables() {
        List<TableEnum> l = null;
        try(Connection conn = MySql.getDataSource().getConnection()) {
            l = new ArrayList<>();
            try(Statement stmt = conn.createStatement()) {
                stmt.execute(Constants.CHECK_USERS_TABLE);
            } catch (SQLException e) {
                //log error
                Main.getLogger().error("Table \"users\" does not exist.");
                l.add(TableEnum.USERS);
            }
            try(Statement stmt = conn.createStatement()) {
                stmt.execute(Constants.CHECK_RECIPES_TABLE);
            } catch (SQLException e) {
                Main.getLogger().error("Table \"recipes\" does not exist");
                l.add(TableEnum.RECIPES);
            }
        } catch (SQLException e) {
            Main.getLogger().error("Failed to check tables.");
            e.printStackTrace();
            System.exit(1);
        }
        return l;
    }

    private static void createTables(List<TableEnum> tables) {
        try(Connection conn = MySql.getDataSource().getConnection()) {
            for(TableEnum table : tables) {
                if(table != null) {
                    switch(table) {
                        case USERS:
                            conn.createStatement().execute(Constants.CREATE_USERS_TABLE);
                            break;
                        case RECIPES:
                            conn.createStatement().execute(Constants.CREATE_RECIPES_TABLE);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            Main.getLogger().error("Failed to create tables.");
            e.printStackTrace();
            System.exit(1);
        }
    }
}

