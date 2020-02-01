package com.wbo.TwitterManager.service;

import com.wbo.TwitterManager.model.dto.TweetDto;
import com.wbo.TwitterManager.model.entity.MyTweet;
import com.wbo.TwitterManager.repo.TwitterRrepo;
import java.util.ArrayList;
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
public class TwitterService {

    @Autowired
    Environment env;

    @Autowired
    TwitterRrepo twitterRrepo;

    public List<TweetDto> searchHashtag(String hashtag) {
        //chercher sur twiter
        List<Tweet> twitterList = searchTwiter(hashtag);
        //chercher en local
        List<MyTweet> localLit = searchLocal(hashtag);
        //alculer la différence entre les 2 premier
        List<MyTweet> diffList = diffLocalTwitter(twitterList, localLit);
        //sauvgarder les nouveau éléments
        saveTweets(diffList);
        //claculer le résultat à rtourner
        for (MyTweet myTweet : diffList) {
            localLit.add(myTweet);
        }
        List<TweetDto> toReturn = localLit.stream()
                .map(t -> new TweetDto(t))
                .collect(Collectors.toList());
        return toReturn;
    }

    private List<Tweet> searchTwiter(String hashtag) {
        String consumerKey = env.getProperty("spring.social.twitter.consumerKey"); // The application's consumer key
        String consumerSecret = env.getProperty("spring.social.twitter.consumerSecret"); // The application's consumer secret
        String accessToken = env.getProperty("spring.social.twitter.app-id"); // The access token granted after OAuth authorization
        String accessTokenSecret = env.getProperty("spring.social.twitter.app-secret");// The access token secret granted after OAuth authorization

        Twitter twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        SearchResults results = twitter.searchOperations().search("#" + hashtag, 20);

        return results.getTweets();
    }

    private List<MyTweet> diffLocalTwitter(List<Tweet> tweets, List<MyTweet> myTweets) {
        if (myTweets != null && myTweets.size() > 0) {
            List<Long> ids = myTweets.stream()
                    .map(t -> t.getId())
                    .collect(Collectors.toList());
            List<Tweet> newList = new ArrayList<>();
            for (Tweet t : tweets) {
                if (!ids.contains(t.getId())) {
                    newList.add(t);
                }
            }
            List<MyTweet> toReturn = newList.stream()
                    .map(t -> new MyTweet(t))
                    .collect(Collectors.toList());
            return toReturn;
        }
        return new ArrayList<>();
    }

    private MyTweet saveTweet(MyTweet tweet) {
        MyTweet res = twitterRrepo.save(tweet);
        return res;
    }

    //pour tester
    public List<TweetDto> getTweets() {
        List<MyTweet> list = twitterRrepo.findAll();
        if (list != null && list.size() > 0) {
            return list.stream().map(t -> new TweetDto(t)).collect(Collectors.toList());
        }
        return null;
    }

    private void saveTweets(List<MyTweet> tweets) {
        for (MyTweet tweet : tweets) {
            saveTweet(tweet);
        }
    }

    private List<MyTweet> searchLocal(String hashtag) {
        return twitterRrepo.findTweetsWithHashtag(hashtag);
    }
}
