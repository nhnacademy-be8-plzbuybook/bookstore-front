package com.nhnacademy.bookstorefront.config;


import com.nhnacademy.bookstorefront.skm.properties.SKMProperties;
import com.nhnacademy.bookstorefront.skm.service.SecureKeyManagerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {

    private final SecureKeyManagerService secureKeyManagerService;
    private final SKMProperties skMProperties;

    public RedisConfig(SecureKeyManagerService secureKeyManagerService, SKMProperties skMProperties) {
        this.secureKeyManagerService = secureKeyManagerService;
        this.skMProperties = skMProperties;
    }

    @Bean
    public String getOrderRedisHost() {
        return secureKeyManagerService.fetchSecret(skMProperties.getVerify_redis().getHost());
    }

    @Bean
    public String getOrderRedisPort(){
        return secureKeyManagerService.fetchSecret(skMProperties.getVerify_redis().getPort());
    }

    @Bean
    public String getOrderRedisPassword() {
        return secureKeyManagerService.fetchSecret(skMProperties.getVerify_redis().getPassword());
    }

    @Bean
    public String getOrderRedisRange(){
        return secureKeyManagerService.fetchSecret(skMProperties.getVerify_redis().getRange());
    }

    @Bean
    public RedisConnectionFactory verifyRedisConnectionFactory() {
        String host = secureKeyManagerService.fetchSecret(skMProperties.getVerify_redis().getHost());
        int port = Integer.parseInt(secureKeyManagerService.fetchSecret(skMProperties.getVerify_redis().getPort()));
        String password = secureKeyManagerService.fetchSecret(skMProperties.getVerify_redis().getPassword());
        int database = Integer.parseInt(secureKeyManagerService.fetchSecret(skMProperties.getVerify_redis().getRange()));

        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(host);
        redisConfig.setPort(port);
        redisConfig.setPassword(password);
        redisConfig.setDatabase(database);

        return new LettuceConnectionFactory(redisConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory verifyRedisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(verifyRedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


}
