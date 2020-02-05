/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wbo.TwitterManager.service;

import com.wbo.TwitterManager.model.entity.MyTweet;
import io.reactivex.Observable;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author b.walid
 */
@Service
public class TwitterProvider {

    @Autowired
    Environment env;

    public List<Tweet> searchTwiter(String hashtag) {
        String consumerKey = env.getProperty("spring.social.twitter.consumerKey"); // The application's consumer key
        String consumerSecret = env.getProperty("spring.social.twitter.consumerSecret"); // The application's consumer secret
        String accessToken = env.getProperty("spring.social.twitter.app-id"); // The access token granted after OAuth authorization
        String accessTokenSecret = env.getProperty("spring.social.twitter.app-secret");// The access token secret granted after OAuth authorization

        Twitter twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        SearchResults results = twitter.searchOperations().search("#" + hashtag, 20);

        return results.getTweets();
    }

    public Observable<List<MyTweet>> getListTweeterObservable(String hashtag) {
        return Observable.create(s -> {
            try {
                s.onNext(searchTwiter(hashtag).stream().map(a -> new MyTweet(a)).collect(Collectors.toList()));
                s.onComplete();
            } catch (Exception e) {
                s.onError(e);
            }
        });
    }
}
