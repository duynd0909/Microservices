/*
 * Copyright (C) FPT Software 2020, Inc - All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.cmc.timesheet.cache.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Redis utils.
 */
@Component
public class RedisUtils {
    private RedisTemplate redisTemplate;

    /**
     * Instantiates a new Redis utils.
     *
     * @param pRedisTemplate the p redis template
     */
    public RedisUtils(final RedisTemplate pRedisTemplate) {
        this.redisTemplate = pRedisTemplate;
    }

    /**
     * Gets members.
     *
     * @param <T> the type parameter
     * @param key the key
     * @return the members
     */
    @SuppressWarnings("unchecked")
    public <T> Set<T> getMembers(final String key) {
        Set<T> sets = (Set<T>) redisTemplate.opsForSet().members(key);
        if (sets != null) {
            return sets;
        } else {
            return new HashSet<>();
        }
    }

    /**
     * Clear all.
     */
    @SuppressWarnings("unchecked")
    public void clearAll() {
        Set<String> keys = (Set<String>) redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }
}
