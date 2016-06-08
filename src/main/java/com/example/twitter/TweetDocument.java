package com.example.twitter;

import lombok.Builder;
import lombok.Value;
import org.springframework.social.twitter.api.Tweet;

import java.util.Date;
import java.util.List;

/**
 * Created by frandayz on 19.01.16.
 */
@Value
@Builder
public class TweetDocument {

    private Long id;
    private String user;
    private String text;
    private Date created;
    private List<String> lemmas;
}
