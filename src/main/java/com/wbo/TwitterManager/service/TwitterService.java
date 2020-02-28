package com.wbo.TwitterManager.service;

import com.wbo.TwitterManager.model.dto.TweetDto;
import com.wbo.TwitterManager.model.entity.MyTweet;
import com.wbo.TwitterManager.repo.TwitterRepo;
import io.reactivex.Maybe;
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

    public List<TweetDto> reactiveSearsh(String hashtag) {
        //chercher sur twiter
        Maybe<Optional<List<Tweet>>> remote = twitterProvider.getListTweeterMaybe(hashtag)
                .defaultIfEmpty(Optional.empty());
        //chercher en local
        Maybe<Optional<List<MyTweet>>> local = getLocalListTweetsMaybe(hashtag)
                .defaultIfEmpty(Optional.empty());

        Maybe<List<TweetDto>> res = Maybe.zip(remote, local, (oRemoteList, oLocalList) -> {
            List<Tweet> remoteList = new ArrayList<>();
            List<MyTweet> localList = new ArrayList<>();
            if (oRemoteList.isPresent()) {
                remoteList = oRemoteList.get();
            }
            if (oLocalList.isPresent()) {
                localList = oLocalList.get();
            }
            //calculer la différence entre les deux listes
            List<MyTweet> differenceist = diffLocalTwitter(remoteList, localList);

            List<MyTweet> toReturn = new ArrayList<>();

            //sauvgarder les nouveaux tweets dans la base de données
            long nextId = twitterRrepo.countTweets();
            for (MyTweet myTweet : differenceist) {
                nextId++;
                myTweet.setId(nextId);
                myTweet = saveTweet(myTweet);
                toReturn.add(myTweet);
            }

            for (MyTweet myTweet : localList) {
                toReturn.add(myTweet);
            }
            //retourner une liste de dto
            return toReturn.stream()
                    .map(t -> new TweetDto(t))
                    .collect(Collectors.toList());
        });

        return res.blockingGet();
    }

    public List<MyTweet> diffLocalTwitter(List<Tweet> tweets, List<MyTweet> myTweets) {

        List<Long> ids = myTweets.stream()
                .map(t -> t.getId())
                .collect(Collectors.toList());
        List<Tweet> newList = new ArrayList<>();
        for (Tweet t : tweets) {
            if (!ids.contains(t.getId())) {
                newList.add(t);
            }
        }
        return newList.stream()
                .map(t -> new MyTweet(t))
                .collect(Collectors.toList());
    }

    private MyTweet saveTweet(MyTweet tweet) {
        return twitterRrepo.save(tweet);
    }

    //pour tester
    public List<TweetDto> getTweets() {
        List<MyTweet> list = twitterRrepo.findAll();
        if (list != null && list.size() > 0) {
            return list.stream().map(t -> new TweetDto(t)).collect(Collectors.toList());
        }
        return null;
    }

    public TweetDto getTweet(Long id) {
        MyTweet tweet = twitterRrepo.findTweetById(id);
        if (tweet != null) {
            return new TweetDto(tweet);
        } else {
            return null;
        }
    }

    private List<MyTweet> searchLocal(String hashtag) {
        return twitterRrepo.findTweetsWithHashtag(hashtag);
    }

    public Maybe<Optional<List<MyTweet>>> getLocalListTweetsMaybe(String hashtag) {
        Maybe<Optional<List<MyTweet>>> maybe = Maybe.create(emitter -> {
            try {
                List<MyTweet> tweets = searchLocal(hashtag);
                if (tweets != null && !tweets.isEmpty()) {
                    emitter.onSuccess(Optional.of(tweets));
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
