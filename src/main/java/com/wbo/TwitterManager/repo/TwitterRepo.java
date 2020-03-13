package com.wbo.TwitterManager.repo;

import com.wbo.TwitterManager.model.entity.MyTweet;
import java.util.List;

/**
 *
 * @author b.walid
 */
public interface TwitterRepo {

    List<MyTweet> findTweetsWithHashtag(String text);

    MyTweet findTweetById(Long id);

    boolean delete(MyTweet mt);

    MyTweet save(MyTweet myTweet);

    List<MyTweet> findAll();

    long maxIds();
}
