package com.wbo.TwitterManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"com.wbo.TwitterManager.repo"})
public class TwitterManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterManagerApplication.class, args);
    }

}
