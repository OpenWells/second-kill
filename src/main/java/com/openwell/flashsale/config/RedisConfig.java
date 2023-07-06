package com.openwell.flashsale.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Liu
 * @date 2023/3/22.
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){


        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        //使用jdk的序列化
        template.setValueSerializer(new FastJsonRedisSerializer<>(Object.class));
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
//        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
//        //key序列化
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        //value序列化
//        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        //hash类型key序列化
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        //hash类型value序列化
//        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//        //注入连接工厂
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        return redisTemplate;
    }

}
