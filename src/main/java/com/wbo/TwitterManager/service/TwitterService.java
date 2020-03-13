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
            List<MyTweet> differenceList = CamputeDifference(remoteList, localList);

            //préparer le résultat.
            List<MyTweet> toReturn = new ArrayList<>(localList);
            toReturn.addAll(differenceList);

            //retourner une liste de dto
            return toReturn.stream()
                    .map(t -> new TweetDto(t))
                    .collect(Collectors.toList());
        });
        return res.blockingGet();
    }

    public List<MyTweet> CamputeDifference(List<Tweet> remoteList, List<MyTweet> localList) {

        List<MyTweet> toReturn = new ArrayList<>();
        for (Tweet tweet : remoteList) {
            boolean b = localList.stream()
                    .filter(t -> t.getId().equals(tweet.getId() + ""))
                    .findFirst()
                    .isPresent();
            if (!b) {
                MyTweet toSave = new MyTweet(tweet);
                twitterRrepo.save(toSave);
                toReturn.add(toSave);
            }
        }
        return toReturn;
    }

    //pour tester
    public List<TweetDto> getTweets() {
        List<MyTweet> list = twitterRrepo.findAll();

        if (list == null && list.isEmpty()) {
            return null;
        }
        return list.stream()
                .map(t -> new TweetDto(t))
                .collect(Collectors.toList());
    }

    //pour tester
    public void removeAllTweets() {
        twitterRrepo.removeAll();
    }

    public TweetDto getTweet(String id) {
        MyTweet tweet = twitterRrepo.findTweetById(id);

        if (tweet == null) {
            return null;
        }
        return new TweetDto(tweet);
    }

    private List<MyTweet> searchLocal(String hashtag) {
        return twitterRrepo.findTweetsWithHashtag(hashtag);
    }

    public Maybe<Optional<List<MyTweet>>> getLocalListTweetsMaybe(String hashtag) {
        return Maybe
                .create(emitter -> {
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
    }
}
