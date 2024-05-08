package com.brewcompanion.brewcomp.auth;

import org.springframework.data.redis.core.script.DigestUtils;

import com.brewcompanion.brewcomp.Main;
import com.brewcompanion.brewcomp.redis.Redis;

import redis.clients.jedis.Jedis;

public class TokenManager {
    public static boolean verifyToken(String token, int userId, String ip) {
        //check to see if the token is valid
        Jedis jedis = Redis.getJedis();
        
        //check to see if token is valid
        if(jedis.get("UID" + userId +"Token:" + token).equals(ip)){
            jedis.close();
            return true;
        }

        jedis.close();
        return false;
    }

    public static String generateToken(int userId, String ip){
        //md5hash the ip and the current time plus a little bit of randomness
        String token = DigestUtils.sha1DigestAsHex(ip + System.currentTimeMillis() + Math.random());
        Jedis jedis = Redis.getJedis();

        //log the token
        Main.getLogger().info(String.format("issued token: %s to user %d", token, userId));

        jedis.set("UID" + userId + "Token:" + token, ip);
        jedis.close();

        return token;

    }

    public static boolean deleteToken(String token, int userId){
        Jedis jedis = Redis.getJedis();
        jedis.del("UID" + userId + "Token:" + token);

        Main.getLogger().info(String.format("deleted token: %s for user %d", token, userId));
        jedis.close();
        return true;
    }
}
