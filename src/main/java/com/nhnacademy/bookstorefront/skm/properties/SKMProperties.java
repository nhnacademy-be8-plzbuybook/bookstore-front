package com.nhnacademy.bookstorefront.skm.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.skm")
public class SKMProperties {
    private String url;
    private String appKey;
    private String keystoreFile;
    private String password;
    private VerifyRedis verify_redis;

   @Getter
   @Setter
   public static class VerifyRedis{
       private String host;
       private String port;
       private String password;
       private String range;
   }
}

