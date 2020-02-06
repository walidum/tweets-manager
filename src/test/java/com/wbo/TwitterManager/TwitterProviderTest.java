/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wbo.TwitterManager;

import com.wbo.TwitterManager.service.TwitterProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author b.walid
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:test.properties")
public class TwitterProviderTest {

    @TestConfiguration
    static class TwitterProviderTesttContextConfiguration {

        @Bean
        public TwitterProvider twitterProvider() {
            return new TwitterProvider();
        }
    }

    @Autowired
    private TwitterProvider twitterProvider;

    @Test
    public void searchShouldReturnResult() {
//        List<Tweet> list = twitterProvider.searchTwiter("#spring");
        //assertThat(list).isNotEmpty();
    }

}
