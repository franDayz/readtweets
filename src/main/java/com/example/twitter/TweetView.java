package com.example.twitter;

import org.springframework.social.twitter.api.Tweet;

import java.util.Date;

/**
 * Created by frandayz on 19.01.16.
 */
public class TweetView {

    private final long id;
    private final String user;
    private final String text;
    private final Date created;

    public TweetView(Tweet tweet) {
        this.id = tweet.getId();
        this.user = tweet.getFromUser();
        this.text = tweet.getText();
        this.created = tweet.getCreatedAt();

    }

    public long getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public Date getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return "TweetView{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", text='" + text + '\'' +
                ", created=" + created +
                '}';
    }
}
