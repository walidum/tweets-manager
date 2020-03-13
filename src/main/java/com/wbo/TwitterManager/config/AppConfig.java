package com.wbo.TwitterManager.config;

import com.mongodb.MongoClient;
import java.rmi.UnknownHostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 *
 * @author b.walid
 */
@Configuration
public class AppConfig {

    @Autowired
    Environment env;

    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        return new SimpleMongoDbFactory(new MongoClient(
                env.getProperty("spring.data.mongodb.host", "localhost"),
                env.getProperty("spring.data.mongodb.port", Integer.class, 27017)),
                env.getProperty("spring.data.mongodb.database", "test"));
    }

    @Bean
    public MongoOperations mongoOperations() throws UnknownHostException {
        return new MongoTemplate(mongoDbFactory());
    }
}
