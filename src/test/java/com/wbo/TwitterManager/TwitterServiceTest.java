package com.wbo.TwitterManager;

import com.wbo.TwitterManager.model.entity.MyTweet;
import com.wbo.TwitterManager.repo.TwitterRepo;
import com.wbo.TwitterManager.service.TwitterProvider;
import com.wbo.TwitterManager.service.TwitterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
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

    @Before
    public void setUp() {
        MyTweet tweet = new MyTweet();
        tweet.setId(123L);
    }

    @Test
    public void listTweetDtoMastNotBeEmpty() {
//        List<TweetDto> list = twitterService.reactiveSearsh("#spring");
        //assertThat(list).isEmpty();
    }
}
