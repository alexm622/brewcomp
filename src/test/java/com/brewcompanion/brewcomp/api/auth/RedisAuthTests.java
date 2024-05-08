package com.brewcompanion.brewcomp.api.auth;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.brewcompanion.brewcomp.Main;
import com.brewcompanion.brewcomp.auth.TokenManager;
import com.brewcompanion.brewcomp.redis.Redis;
import com.redis.testcontainers.RedisContainer;

import java.time.Duration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Jedis;

@Testcontainers
class RedisAuthTests {

    @Container
    private static RedisContainer redisContainer = new RedisContainer(
            RedisContainer.DEFAULT_IMAGE_NAME.withTag(RedisContainer.DEFAULT_TAG));

    @BeforeAll
    static void setupRedis() {
        redisContainer.start();

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(1);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleDuration(Duration.ofSeconds(1));
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);

        // log the uri
        Main.getLogger().error("Redis URI: " + redisContainer.getRedisURI());

        // initialize a jedispool with the container's uri

        JedisPool pool = new JedisPool(poolConfig, redisContainer.getRedisURI());

        Redis.setJedisPool(pool);
    }

    @AfterAll
    static void stopContainer() {
        redisContainer.stop();
    }

    @Test
    void tokenCreateTest() {
        // test token creation
        Jedis j = Redis.getJedis();

        String token = TokenManager.generateToken(0, "66.66.66.66");

        assertTrue(j.exists("UID0Token:" + token));
    }

    @Test
    void tokenVerifyTest() {

        // insert a test token
        Jedis j = Redis.getJedis();
        j.set("UID0Token:" + "testtoken", "66.66.66.66");

        // test token verification
        assertTrue(TokenManager.verifyToken("testtoken", 0, "66.66.66.66"));
    }
}
