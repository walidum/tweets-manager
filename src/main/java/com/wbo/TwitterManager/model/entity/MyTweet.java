package com.wbo.TwitterManager.model.entity;

import java.io.Serializable;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.social.twitter.api.Tweet;

/**
 *
 * @author b.walid
 */
@Document
public class MyTweet implements Serializable {

    @Id
    private Long id;
    private String idStr;
    private String text;
    private Date createdAt;
    private String fromUser;
    private String languageCode;
    private String source;

    public MyTweet() {
    }

    public MyTweet(Tweet tweet) {
        this.id = tweet.getId();
        this.createdAt = tweet.getCreatedAt();
        this.fromUser = tweet.getFromUser();
        this.text = tweet.getText();
        this.languageCode = tweet.getLanguageCode();
        if (tweet.getUser() != null) {
            this.source = tweet.getUser().getProfileImageUrl();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MyTweet)) {
            return false;
        }
        MyTweet other = (MyTweet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wbo.TwitterManager.model.entity.Tweet[ id=" + id + " ]";
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

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
