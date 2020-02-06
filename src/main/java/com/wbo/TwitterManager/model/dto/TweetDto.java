package com.wbo.TwitterManager.model.dto;

import com.wbo.TwitterManager.model.entity.MyTweet;
import java.util.Date;
import org.springframework.social.twitter.api.Tweet;

/**
 *
 * @author b.walid
 */
public class TweetDto {

    private long id;
    private String idStr;
    private String text;
    private Date createdAt;
    private String fromUser;
    private String languageCode;
    private String source;

    public TweetDto() {
    }

    public TweetDto(MyTweet tweet) {
        this.id = tweet.getId();
        this.createdAt = tweet.getCreatedAt();
        this.fromUser = tweet.getFromUser();
        this.text = tweet.getText();
        this.languageCode = tweet.getLanguageCode();
        this.source = tweet.getSource();
    }

    public TweetDto(Tweet tweet) {
        this.id = tweet.getId();
        this.createdAt = tweet.getCreatedAt();
        this.fromUser = tweet.getFromUser();
        this.text = tweet.getText();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TweetDto{" + "id=" + id + ", idStr=" + idStr + ", text=" + text + ", createdAt=" + createdAt + ", fromUser=" + fromUser + ", languageCode=" + languageCode + ", source=" + source + '}';
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

}
