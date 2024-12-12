package com.nhnacademy.bookstorefront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BookstoreFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreFrontApplication.class, args);
    }

}
