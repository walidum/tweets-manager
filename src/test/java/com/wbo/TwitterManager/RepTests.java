package com.wbo.TwitterManager;

import com.wbo.TwitterManager.model.entity.MyTweet;
import com.wbo.TwitterManager.repo.TwitterRepo;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author b.walid
 */
@RunWith(SpringRunner.class)
public class RepTests {

    @MockBean
    private TwitterRepo twitterRepo;

    @Before
    public void setUp() {
        MyTweet mt = new MyTweet();
        long id = 9999;
        mt.setId(id);

        Mockito.when(twitterRepo.findTweetById(id))
                .thenReturn(mt);
    }

    @Test
    public void whenValidIdTweetShouldBeFound() {
        long id = 9999;
        //find the tweet and chek if the result is not null
        MyTweet tweet = twitterRepo.findTweetById(id);
        assertThat(tweet.getId()).isEqualTo(id);
    }

}
