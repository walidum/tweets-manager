package com.wbo.TwitterManager.service;

import com.wbo.TwitterManager.model.dto.TweetDto;
import com.wbo.TwitterManager.model.entity.MyTweet;
import com.wbo.TwitterManager.repo.TwitterRepo;
import io.reactivex.Maybe;
import java.util.ArrayList;
import java.util.Arrays;
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

    public List<TweetDto> reactiveSearsh(String hashtag) {
        //chercher sur twiter
        Maybe<List<Tweet>> remote = twitterProvider.getListTweeterMaybe(hashtag);
        //chercher en local
        MyTweet test = new MyTweet();
        test.setId(21212313213L);
        Maybe<List<MyTweet>> local = Maybe.just(Arrays.asList(test));

        Maybe<List<TweetDto>> res = Maybe.zip(remote, local, (remoteList, localList) -> {
            //calculer la différence entre les 2
            List<MyTweet> differenceist = diffLocalTwitter(remoteList, localList);

            List<MyTweet> toReturn = new ArrayList<>();

            for (MyTweet myTweet : differenceist) {
                saveTweet(myTweet);
                toReturn.add(myTweet);
            }

            for (MyTweet myTweet : localList) {
                toReturn.add(myTweet);
            }

            return toReturn.stream()
                    .map(t -> new TweetDto(t))
                    .collect(Collectors.toList());

        });

        List<TweetDto> toReturn = res.blockingGet();
        return toReturn;
    }

    public List<TweetDto> search(String hashtag) {
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

    public Maybe<List<MyTweet>> getLocalListTweetsMaybe(String hashtag) {
        Maybe<List<MyTweet>> maybe = Maybe.create(emitter -> {
            try {
                List<MyTweet> tweets = searchLocal(hashtag);
                if (tweets != null && !tweets.isEmpty()) {
                    emitter.onSuccess(tweets);
                } else {
                    emitter.onComplete();
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
        return maybe;
    }
}
