package com.example.twitter;

import com.example.twitter.lemmatizer.LemmatizerService;
import com.example.twitter.scrapper.ScrapperService;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import rx.Observable;

/**
 * Created by frandayz on 09.01.16.
 */
@Service
public class TimeLineService {

    private Twitter twitter;
    private ScrapperService scrapperService;
    private LemmatizerService lemmatizerService;

    @Inject
    public TimeLineService(Twitter twitter,
                           ScrapperService scrapperService,
                           LemmatizerService lemmatizerService) {

        this.twitter = twitter;
        this. scrapperService = scrapperService;
        this.lemmatizerService = lemmatizerService;
    }

    /**
     * @param userId id of the user we want to get the tweets from
     */
    public List<TweetDocument> getTimeLine(String userId) {
        List<Tweet> tweets = twitter.timelineOperations()
                .getUserTimeline(userId, 200);

        return tweets.stream()
                .map(this::buildTweetDocument)
                .collect(Collectors.toList());
    }

    public Observable<TweetDocument> getTimeLineAsync(String userId) {
        return Observable.<TweetDocument>create(s -> {
            List<Tweet> tweets = twitter.timelineOperations()
                    .getUserTimeline(userId, 10);

            tweets.stream()
                    .map(this::buildTweetDocument)
                    .forEach(s::onNext);

            s.onCompleted();
        });
    }

    private TweetDocument buildTweetDocument(Tweet tweet) {
        Optional<String> url = extractUrl(tweet.getText());

        Optional<String> document = url.map(scrapperService::scrapUrl)
                .orElse(Optional.empty());

        List<String> lemmas = document.map(lemmatizerService::lemmatize)
                .orElse(new ArrayList<>());

        return TweetDocument.builder()
                .id(tweet.getId())
                .user(tweet.getFromUser())
                .text(tweet.getText())
                .lemmas(lemmas)
                .created(tweet.getCreatedAt())
                .build();
    }

    private Optional<String> extractUrl(String text) {
        Pattern pattern = Pattern.compile("http[s]{0,1}://\\S+");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return Optional.of(matcher.group());
        } else {
            return Optional.empty();
        }
    }
}
