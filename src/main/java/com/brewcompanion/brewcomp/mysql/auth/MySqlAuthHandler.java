package com.brewcompanion.brewcomp.mysql.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

import com.brewcompanion.brewcomp.Main;
import com.brewcompanion.brewcomp.mysql.Constants;
import com.brewcompanion.brewcomp.mysql.MySql;

public class MySqlAuthHandler {
    public static boolean verifyLogin(String username, String password) {
        // verify the login

        try (Connection conn = MySql.getDataSource().getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(Constants.GET_SALT)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String salt = rs.getString("salt");
                        // check to see if the password is correct
                        // the hashing algorithm is bcrypt
                        return new BCryptPasswordEncoder().matches(password, salt);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public static int getUserId(String username) {
        try (Connection conn = MySql.getDataSource().getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(Constants.GET_USER_ID)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Check to see if given username exists
     * @param username
     * @return true if username exists
     */
    public static boolean doesUsernameExist(String username) {
        try (Connection conn = MySql.getDataSource().getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(Constants.USERNAME_IN_USE)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get a username given id
     * @param id uid
     * @return the username
     */
    public static String getUsername(int id) {
        try (Connection conn = MySql.getDataSource().getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(Constants.GET_USERNAME)) {
                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("username");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    /**
     * insert the user into the database
     * @param username
     * @param email
     * @param password
     * @return
     */
    public static boolean insertUser(String username, String email, String password) {
        // insert user into database
        try (Connection conn = MySql.getDataSource().getConnection();) {
            try (PreparedStatement pstmt = conn.prepareStatement(Constants.INSERT_USER)) {

                String hashme = username + email;
                //md5 hash the username and email
                String hash = DigestUtils.md5DigestAsHex(hashme.getBytes());

                Main.getLogger().info("Hash: " + hash);

                //generate the bcrypt salted password hash
                String salt = new BCryptPasswordEncoder().encode(password);


                pstmt.setString(1, username);
                pstmt.setString(2, hash);
                pstmt.setString(3, email);
                pstmt.setString(4, salt);

                pstmt.execute();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    

}
