package com.krab.burger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate();
        // 默认的Key序列化器为: JdkSerializationRedisSerializer, 序列化对象时key可能会出现奇怪字符
        // 改成StringRedisSerializer方便阅读
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);// key:采用字符串原文序列化保存
        template.setValueSerializer(stringRedisSerializer);// value:采用字符串原文序列化保存
        template.setHashKeySerializer(stringRedisSerializer);
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
