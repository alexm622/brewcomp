package com.brewcompanion.brewcomp.redis;

import java.time.Duration;

import com.brewcompanion.brewcomp.Main;
import com.brewcompanion.brewcomp.utils.Config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Redis {
    private static JedisPool pool;

    public static void intialize() {
        
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        final Config config = Main.getConfig();
        poolConfig.setMaxTotal(config.getRedisMaxPoolSize());
        poolConfig.setMaxIdle(config.getRedisMaxIdle());
        poolConfig.setMinIdle(config.getRedisMinIdle());
        poolConfig.setTestOnBorrow(config.getRedisTestOnBorrow());
        poolConfig.setTestOnReturn(config.getRedisTestOnReturn());
        poolConfig.setTestWhileIdle(config.getRedisTestWhileIdle());
        poolConfig.setMinEvictableIdleDuration(Duration.ofSeconds(config.getRedisMinEvictableIdleDuration()));
        poolConfig.setNumTestsPerEvictionRun(config.getRedisNumTestsPerEvictionRun());
        poolConfig.setBlockWhenExhausted(config.getRedisBlockWhenExhausted());

        pool = new JedisPool(poolConfig, config.getRedisHost(), config.getRedisPort());
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }
}
