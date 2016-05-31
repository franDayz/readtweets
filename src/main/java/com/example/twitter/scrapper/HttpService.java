package com.example.twitter.scrapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by frandayz on 31.05.16.
 */
@Service
public class HttpService {

    public Optional<String> performGetRequest(String url) {
        HttpClient httpClient = HttpClientBuilder.create()
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build();

        HttpGet request = new HttpGet(url);
        BasicResponseHandler handler = new BasicResponseHandler();

        try {
            HttpResponse response = httpClient.execute(request);
            return Optional.of(handler.handleResponse(response));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
