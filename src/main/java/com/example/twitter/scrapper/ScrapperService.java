package com.example.twitter.scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by frandayz on 31.05.16.
 */
@Service
public class ScrapperService {

    private HttpService httpService;

    @Inject
    public ScrapperService(HttpService httpService) {
        this.httpService = httpService;
    }

    public Optional<String> scrap(String html) {
        Document doc = Jsoup.parse(html);
        Elements paragraphs = doc.select("body");
        String text = paragraphs.text();

        if (!StringUtils.isEmpty(text)) {
            return Optional.of(text);
        } else {
            return Optional.empty();
        }
    }

    public Optional<String> scrapUrl(String url) {
        Optional<String> html = httpService.performGetRequest(url);

        if (html.isPresent()) {
            return scrap(html.get());
        } else {
            return Optional.empty();
        }
    }
}
