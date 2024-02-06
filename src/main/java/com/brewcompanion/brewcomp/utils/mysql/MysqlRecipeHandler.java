package com.brewcompanion.brewcomp.utils.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.brewcompanion.brewcomp.objects.Recipe;
import com.brewcompanion.brewcomp.objects.User;

public class MysqlRecipeHandler {
    public static boolean insertRecipe(Recipe recipe) {
        // insert recipe into database

        try(Connection conn = MySql.getDataSource().getConnection()){
            try (PreparedStatement pstmt = conn.prepareStatement(Constants.INSERT_RECIPE)) {
                pstmt.setString(1, recipe.getName());
                pstmt.setString(2, recipe.getHash());
                pstmt.setString(3, recipe.getAuthor());
                pstmt.setString(4, recipe.getAuthorHash());
                pstmt.setInt(5, recipe.getAuthorID());
                pstmt.setString(6, recipe.getDescription());
                pstmt.setString(7, recipe.getRecipeInstructions());
                pstmt.setString(8, recipe.getRecipeType());
                pstmt.setString(9, recipe.getParentName());
                pstmt.setString(10, recipe.getParentHash());
                pstmt.setInt(11, recipe.getParentID());
                pstmt.setString(12, recipe.getParentAuthor());
                pstmt.setString(13, recipe.getParentAutherHash());

                pstmt.execute();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }


        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public static boolean insertUser(User user){
        // insert user into database
        try(Connection conn = MySql.getDataSource().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(Constants.INSERT_USER)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getHash());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getSalt());

            pstmt.execute();
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    
}
