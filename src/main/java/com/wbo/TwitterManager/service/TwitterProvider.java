package com.wbo.TwitterManager.service;

import io.reactivex.Maybe;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.twitter.api.SearchParameters;
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
        if (hashtag == null || hashtag.isEmpty()) {
            return new ArrayList<>();
        }

        //region on a besoin de ces lignes pour chaque requete ?
        //twitter app
        String consumerKey = env.getProperty("spring.social.twitter.consumerKey", ""); // The application's consumer key
        String consumerSecret = env.getProperty("spring.social.twitter.consumerSecret", ""); // The application's consumer secret
        String accessToken = env.getProperty("spring.social.twitter.app-id", ""); // The access token granted after OAuth authorization
        String accessTokenSecret = env.getProperty("spring.social.twitter.app-secret", "");// The access token secret granted after OAuth authorization

        Twitter twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);

        if (!hashtag.startsWith("#")) {
            hashtag = "#" + hashtag;
        }
        Integer nb_max = env.getProperty("twitter.nb.max.tweets", Integer.class, 20);
        //endregion

        SearchParameters params = new SearchParameters(hashtag)
                .resultType(SearchParameters.ResultType.RECENT)
                .count(nb_max);
        SearchResults results = twitter.searchOperations().search(params);
        return results.getTweets();
    }

    public Maybe<Optional<List<Tweet>>> getListTweeterMaybe(String hashtag) {
        return Maybe
                .create(emitter -> {
                    try {
                        List<Tweet> tweets = searchTwiter(hashtag);
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
