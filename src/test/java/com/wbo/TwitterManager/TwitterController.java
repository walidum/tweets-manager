/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wbo.TwitterManager;

import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author b.walid
 */
@RestController
public class TwitterController {

    @GetMapping("/hi")
    public String sayHello() {
        return "Hello";
    }

    @GetMapping("/tweets")
    public String getTweets() {
        String consumerKey = "Q8xA2R7JzpYOjkYcfdsBOMSTu"; // The application's consumer key
        String consumerSecret = "RTiV4LxNiVsgdp7TWWmMW57P4m5Ys65yCa6t36Zkk5b3azwHOb"; // The application's consumer secret
        String accessToken = "3044887263-uJQKpewfl5xb2ewlGGCOJ00I0GeghJh5FJiSLAS"; // The access token granted after OAuth authorization
        String accessTokenSecret = "mqmsGikxmPInW6MWHxbfLc6WzHljNiBKeadSSCVDBPvjI"; // The access token secret granted after OAuth authorization
        Twitter twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        SearchResults results = twitter.searchOperations().search("#spring");

        return results.getTweets().size() + "";
    }

}
