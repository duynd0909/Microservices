package com.cmc.timesheet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    //    @Value("${spring.redis.password}")
//    private String redisPassword;
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration
                = new RedisStandaloneConfiguration(redisHost,
                Integer.parseInt(redisPort));
//        if (!ObjectUtils.isEmpty(redisPassword)) {
//            redisStandaloneConfiguration.setPassword(
//                    RedisPassword.of(redisPassword));
//        }
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(500);
        poolConfig.setMinIdle(2);
        poolConfig.setMaxIdle(5);
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        connectionFactory.setPoolConfig(poolConfig);
        return connectionFactory;
    }

    /**
     * Redis template redis template.
     *
     * @return the redis template
     */
    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(
                new GenericToStringSerializer<>(Object.class));
        template.setDefaultSerializer(new StringRedisSerializer());
        return template;
    }

//    /**
//     * Redis custom conversions redis custom conversions.
//     *
//     * @param offsetToBytes the offset to bytes
//     * @param bytesToOffset the bytes to offset
//     * @return the redis custom conversions
//     */
//    @Bean
//    public RedisCustomConversions redisCustomConversions(
//            final OffsetDateTimeToBytesConverter offsetToBytes,
//            final BytesToOffsetDateTimeConverter bytesToOffset) {
//        return new RedisCustomConversions(Arrays.asList(offsetToBytes,
//                bytesToOffset));
//    }

}
