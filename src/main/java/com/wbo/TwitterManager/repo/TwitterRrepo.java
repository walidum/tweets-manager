package com.wbo.TwitterManager.repo;

import com.wbo.TwitterManager.model.entity.MyTweet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author b.walid
 */
public interface TwitterRrepo extends JpaRepository<MyTweet, Long> {

    @Query("SELECT t FROM MyTweet t WHERE lower(t.text) LIKE lower(CONCAT('%', :hashtag,'%'))")
    List<MyTweet> findTweetsWithHashtag(@Param("hashtag") String hashtag);

}
