/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.balance_dispute_service.redis.config;

import com.asset.ccat.balance_dispute_service.dto.responses.BalanceDisputeReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 *
 * @author wael.mohamed
 */
@Configuration
@EnableRedisRepositories(basePackages = "com.asset.ccat.gateway.redis.repository")
public class RedisConfiguration {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(
                redisProperties.getHostName(),
                redisProperties.getPortNumber()
        );
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, BalanceDisputeReportResponse> redisTemplateForBalanceDisputeReport() {
        RedisTemplate<String, BalanceDisputeReportResponse> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
