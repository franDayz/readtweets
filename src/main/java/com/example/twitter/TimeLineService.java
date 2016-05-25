package com.example.twitter;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by frandayz on 09.01.16.
 */
@Service
public class TimeLineService {

    private Twitter twitter;

    @Inject
    public TimeLineService(Twitter twitter) {
        this.twitter = twitter;
    }

    /**
     * @param userId id of the user we want to get the tweets from
     */
    public List<TweetView> getTimeLine(String userId) {
        List<Tweet> tweets = this.twitter.timelineOperations().getUserTimeline(userId, 200);
        return tweets.stream()
                .map(t -> new TweetView(t))
                .collect(Collectors.toList());
    }
}
