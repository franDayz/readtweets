package com.example.twitter;

import org.springframework.http.ResponseEntity;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
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
    public ResponseEntity<List<TweetView>> getTimeLine(@RequestParam(required = true) String user) {
        List<TweetView> tweets = this.timeLineService.getTimeLine(user);
        return ResponseEntity.ok(tweets);
    }
}
