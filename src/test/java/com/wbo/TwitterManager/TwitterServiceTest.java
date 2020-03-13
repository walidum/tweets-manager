package com.wbo.TwitterManager;

import com.wbo.TwitterManager.model.entity.MyTweet;
import com.wbo.TwitterManager.repo.TwitterRepo;
import com.wbo.TwitterManager.service.TwitterProvider;
import com.wbo.TwitterManager.service.TwitterService;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class TwitterServiceTest {

    @TestConfiguration
    static class TwitterServiceTestContextConfiguration {

        @Bean
        public TwitterProvider twitterProvider() {
            return new TwitterProvider();
        }

        @Bean
        public TwitterService employeeService() {
            return new TwitterService();
        }
    }
    @Autowired
    TwitterService twitterService;
    @MockBean
    private TwitterRepo twitterRepo;

    @Test
    public void diffBetween2List() {
        MyTweet tweet = new MyTweet();
        Tweet tweet1 = new Tweet(0, "text", new Date(), "fromUser",
                "profileImageUrl", Long.MIN_VALUE, 0, "languageCode", "source");
        tweet.setId("123");
        List<Tweet> list1 = Arrays.asList(tweet1);
        List<MyTweet> list2 = Arrays.asList(tweet);
//        List<MyTweet> list = twitterService.diffLocalTwitter(list1);
//        assertThat(list).isNotEmpty();
    }
}
