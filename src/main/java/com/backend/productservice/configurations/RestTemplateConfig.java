package com.backend.productservice.configurations;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;

@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced
    //for when a list of URLs are passed using the eureka server.
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    //string for the id stuff and a generic Object for any kind of Object.
    public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
