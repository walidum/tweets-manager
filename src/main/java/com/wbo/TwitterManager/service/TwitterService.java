package com.wbo.TwitterManager.service;

import com.wbo.TwitterManager.model.dto.TweetDto;
import com.wbo.TwitterManager.model.entity.MyTweet;
import com.wbo.TwitterManager.repo.TwitterRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.twitter.api.Tweet;
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
    TwitterProvider twitterProvider;
    @Autowired
    TwitterRepo twitterRrepo;

    public List<TweetDto> searchHashtag(String hashtag) {
        //chercher sur twiter
        List<Tweet> twitterList = twitterProvider.searchTwiter(hashtag);
        //chercher en local
        List<MyTweet> localLit = searchLocal(hashtag);
        //calculer la différence entre les 2
        List<MyTweet> diffList = diffLocalTwitter(twitterList, localLit);
        //sauvgarder les nouveaux éléments
        saveTweets(diffList);
        //claculer le résultat à retourner
        for (MyTweet myTweet : diffList) {
            localLit.add(myTweet);
        }
        List<TweetDto> toReturn = localLit.stream()
                .map(t -> new TweetDto(t))
                .collect(Collectors.toList());
        return toReturn;
    }

    private List<MyTweet> diffLocalTwitter(List<Tweet> tweets, List<MyTweet> myTweets) {

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

    private MyTweet saveTweet(MyTweet tweet) {
//        MyTweet res = twitterRrepo.save(tweet);
        return null;
    }

    //pour tester
    public List<TweetDto> getTweets() {
//        List<MyTweet> list = twitterRrepo.findAll();
//        if (list != null && list.size() > 0) {
//            return list.stream().map(t -> new TweetDto(t)).collect(Collectors.toList());
//        }
        return null;
    }

    private void saveTweets(List<MyTweet> tweets) {
        for (MyTweet tweet : tweets) {
            saveTweet(tweet);
        }
    }

    public TweetDto getTweet(Long id) {
        Optional<MyTweet> tweet = twitterRrepo.findById(id);
        if (tweet.isPresent()) {
            return new TweetDto(tweet.get());
        } else {
            return null;
        }
    }

    private List<MyTweet> searchLocal(String hashtag) {
        return twitterRrepo.findTweetsWithHashtag(hashtag);
    }
}
