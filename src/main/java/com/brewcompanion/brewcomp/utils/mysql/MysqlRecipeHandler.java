package com.brewcompanion.brewcomp.utils.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.brewcompanion.brewcomp.objects.Recipe;

public class MysqlRecipeHandler {
    public static boolean insertRecipe(Recipe recipe) {
        // insert recipe into database

        try(Connection conn = MySql.getDataSource().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(Constants.INSERT_RECIPE);
            pstmt.setString(1, recipe.getName());
            pstmt.setString(2, recipe.getHash());
            pstmt.setString(3, recipe.getAuthor());
            pstmt.setString(4, recipe.getAuthorHash());
            
            pstmt.setString(6, recipe.getDescription());

        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }
}
