package com.nhnacademy.bookstorefront.common.config;

import com.nhnacademy.bookstorefront.skm.properties.SKMProperties;
import com.nhnacademy.bookstorefront.skm.service.SecureKeyManagerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60 * 60)
public class RedisSessionConfig {


    private final SecureKeyManagerService secureKeyManagerService;
    private final SKMProperties skMProperties;

    public RedisSessionConfig(SecureKeyManagerService secureKeyManagerService, SKMProperties skMProperties) {
        this.secureKeyManagerService = secureKeyManagerService;
        this.skMProperties = skMProperties;
    }

    @Bean
    public String getSessionRedisHost() {
        //인증 레디스 서버와 host, port, password 가 동일해서 가져옴.
        return secureKeyManagerService.fetchSecret(skMProperties.getVerify_redis().getHost());
    }

    @Bean
    public String getSessionRedisPort() {
        return secureKeyManagerService.fetchSecret(skMProperties.getVerify_redis().getPort());
    }

    @Bean
    public String getSessionRedisPassword() {
        return secureKeyManagerService.fetchSecret(skMProperties.getVerify_redis().getPassword());
    }

    @Primary
    @Bean(name = "getSessionRedisFactory")
    public RedisConnectionFactory getSessionRedisFactory() {
        String host = getSessionRedisHost();
        int port = Integer.parseInt(getSessionRedisPort());
        String password = getSessionRedisPassword();

        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(host);
        redisConfig.setPort(port);
        redisConfig.setPassword(password);
        redisConfig.setDatabase(235);
        return new LettuceConnectionFactory(redisConfig);
    }


    @Bean(name = "sessionRedisTemplate")
    public RedisTemplate<String, Object> sessionRedisTemplate(@Qualifier("getSessionRedisFactory") RedisConnectionFactory getSessionRedisFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(getSessionRedisFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
