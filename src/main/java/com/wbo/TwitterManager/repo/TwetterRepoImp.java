package com.wbo.TwitterManager.repo;

import com.wbo.TwitterManager.model.entity.MyTweet;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author b.walid
 */
@Repository
public class TwetterRepoImp implements TwitterRepo {

    @Autowired
    MongoOperations mongoOperations;

    @Override
    public List<MyTweet> findTweetsWithHashtag(String text) {
        Criteria regex = Criteria.where("text").regex(Pattern.compile(text, Pattern.CASE_INSENSITIVE));
        return mongoOperations.find(new Query().addCriteria(regex), MyTweet.class);
    }

    @Override
    public MyTweet findTweetById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoOperations.findOne(query, MyTweet.class);
    }

    @Override
    public MyTweet save(MyTweet myTweet) {
        try {
            return mongoOperations.save(myTweet);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<MyTweet> findAll() {
        return mongoOperations.findAll(MyTweet.class);
    }

    @Override
    public void removeAll() {
        List<MyTweet> list = mongoOperations.findAll(MyTweet.class);
        for (MyTweet myTweet : list) {
            mongoOperations.remove(myTweet);
        }
    }

    @Override
    public boolean delete(MyTweet mt) {
        try {
            mongoOperations.remove(mt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
