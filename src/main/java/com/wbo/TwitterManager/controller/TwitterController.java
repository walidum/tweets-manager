/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wbo.TwitterManager.controller;

import com.wbo.TwitterManager.model.dto.TweetDto;
import com.wbo.TwitterManager.service.TwitterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author b.walid
 */
@RestController
public class TwitterController {

    @Autowired
    TwitterService twitterService;

    @RequestMapping("/")
    public String sayHello() {
        return "Hello";
    }

    @GetMapping("/searsh/{hashtag}")
    public List<TweetDto> getTweets(@PathVariable("hashtag") String hashtag) {
        return twitterService.searchHashtag(hashtag);
    }

    @GetMapping("/all")
    public List<TweetDto> getAllTweets() {
        return twitterService.getTweets();
    }

}
