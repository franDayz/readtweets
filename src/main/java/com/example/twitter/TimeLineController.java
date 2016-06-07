package com.example.twitter;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

/**
 * Created by frandayz on 10.01.16.
 */
@Controller
public class TimeLineController {

    private TimeLineService timeLineService;

    @Inject
    public TimeLineController(TimeLineService timeLineService) {
        this.timeLineService = timeLineService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/timeline")
    public ResponseEntity<List<TweetView>> getTimeLine(
            @RequestParam(required = true) String user) {

        List<TweetView> tweets = timeLineService.getTimeLine(user);
        return ResponseEntity.ok(tweets);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/timeline/async")
    public SseEmitter getTimeLineAsync(
            @RequestParam(required = true) String user) {

        final SseEmitter sseEmitter = new SseEmitter();
        timeLineService.getTimeLineAsync(user)
                .subscribe(
                        tweet -> sendTweet(sseEmitter, tweet),
                        sseEmitter::completeWithError,
                        sseEmitter::complete
                );
        return sseEmitter;
    }

    private void sendTweet(SseEmitter sseEmitter, TweetView tweet) {
        try {
            sseEmitter.send(tweet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
