package com.wbo.TwitterManager.repo;

import com.wbo.TwitterManager.model.entity.MyTweet;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author b.walid
 */
public interface TwitterRepo extends MongoRepository<MyTweet, Long> {

    @Query("{'text': {$regex: ?0 }})")
    List<MyTweet> findTweetsWithHashtag(String text);
}
//use admin
//db.createUser(
//  {
//    user: "root",
//    pwd: "root",
//    roles: [ { role: "userAdminAnyDatabase", db: "admin" },
//             { role: "dbAdminAnyDatabase", db: "admin" },
//             { role: "readWriteAnyDatabase", db: "admin" } ]
//  }
//)
