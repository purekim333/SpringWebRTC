package com.backend.springwebrtc.room.config;

import com.backend.springwebrtc.room.redis.RedisMessageSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // RedisTemplate 커스터마이징: Key/Value를 String으로 직렬화
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer()); // Key 직렬화
        template.setValueSerializer(new StringRedisSerializer()); // Value 직렬화
        return template;
    }

    // Pub/Sub 채널 설정
    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("global-events");
    }

    // RedisMessageListenerContainer 설정 (Pub/Sub 구독)
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory,
            RedisMessageSubscriber subscriber,
            ChannelTopic topic) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(subscriber, topic); // Subscriber와 채널 연결
        return container;
    }
}
