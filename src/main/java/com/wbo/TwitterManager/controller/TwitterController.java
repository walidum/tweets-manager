package com.wbo.TwitterManager.controller;

import com.wbo.TwitterManager.exeption.JsonResponse;
import com.wbo.TwitterManager.model.dto.TweetDto;
import com.wbo.TwitterManager.service.TwitterService;
import java.util.List;
import javax.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author b.walid
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TwitterController {

    @Autowired
    TwitterService twitterService;

    @RequestMapping("/")
    public String sayHello() {
        return "Hello";
    }

    @GetMapping("/search/{hashtag}")
    public ResponseEntity<JsonResponse> getTweets(@PathVariable("hashtag") String hashtag) throws ValidationException {
        if (hashtag == null || hashtag.isEmpty()) {
            throw new ValidationException("Le champ hashtag est obligatoir !");
        }
        List<TweetDto> data = twitterService.reactiveSearsh(hashtag);
        JsonResponse jsonResponse = new JsonResponse();
        if (data != null && !data.isEmpty()) {
            jsonResponse.setData(data);
        }
        jsonResponse.setStatus(JsonResponse.STATUS.SUCCESS.name());
        return ResponseEntity.ok(jsonResponse);
    }

    @GetMapping("/get/tweet/{id}")
    public ResponseEntity<JsonResponse> getTweet(@PathVariable("id") String id) throws ValidationException {
        if (id == null) {
            throw new ValidationException("Le champ id est obligatoir !");
        }
        TweetDto data = twitterService.getTweet(id);
        JsonResponse jsonResponse = new JsonResponse();
        if (data != null) {
            jsonResponse.setData(data);
        }
        jsonResponse.setStatus(JsonResponse.STATUS.SUCCESS.name());
        return ResponseEntity.ok(jsonResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<JsonResponse> getAllTweets() {
        List<TweetDto> data = twitterService.getTweets();
        JsonResponse jsonResponse = new JsonResponse();
        if (data != null && !data.isEmpty()) {
            jsonResponse.setData(data);
        }
        jsonResponse.setStatus(JsonResponse.STATUS.SUCCESS.name());
        return ResponseEntity.ok(jsonResponse);
    }

}
