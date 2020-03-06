package com.wbo.TwitterManager;

import com.wbo.TwitterManager.service.TwitterProvider;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.social.twitter.api.Tweet;
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
        List<Tweet> list = twitterProvider.searchTwiter("#spring");
        assertThat(list).isNotEmpty();
    }

}
