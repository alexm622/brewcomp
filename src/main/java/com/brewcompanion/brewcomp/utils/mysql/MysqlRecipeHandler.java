package com.brewcompanion.brewcomp.utils.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.brewcompanion.brewcomp.objects.Recipe;
import com.brewcompanion.brewcomp.objects.User;

public class MysqlRecipeHandler {
    public static boolean insertRecipe(Recipe recipe) {
        // insert recipe into database

        try(Connection conn = MySql.getDataSource().getConnection()){
            try (PreparedStatement pstmt = conn.prepareStatement(Constants.INSERT_RECIPE)) {
                pstmt.setInt(1, recipe.getId());
                pstmt.setString(2, recipe.getName());
                pstmt.setString(3, recipe.getHash());
                pstmt.setString(4, recipe.getAuthor());
                pstmt.setInt(5, recipe.getAuthorID());
                pstmt.setString(6, recipe.getAuthorHash());
                pstmt.setString(7, recipe.getDescription());
                pstmt.setString(8, recipe.getRecipeInstructions());
                pstmt.setString(9, recipe.getRecipeType());
                pstmt.setString(10, recipe.getParentName());
                pstmt.setString(11, recipe.getParentHash());
                pstmt.setInt(12, recipe.getParentID());
                pstmt.setString(13, recipe.getParentAuthor());
                pstmt.setString(14, recipe.getParentAutherHash());

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

    public static Recipe getRecipe(int id){
        ResultSet rs = null;
        try(Connection conn = MySql.getDataSource().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(Constants.GET_RECIPE)) {
            pstmt.setInt(1, id); //id
            pstmt.setInt(2, 1); //limit
            rs = pstmt.executeQuery();
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
        try{
            return Recipe.fromResultSet(rs);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        

    }
    
}
