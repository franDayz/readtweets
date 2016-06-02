package com.example.twitter.scrapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Optional;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

/**
 * Created by frandayz on 02.06.16.
 */
@RunWith(JUnit4.class)
public class HttpServiceTest {

    private HttpService httpService = new HttpService();

    @Test
    public void shouldFollowRedirects() {
        Optional<String> html =
                httpService.performGetRequest("https://t.co/97mGjADA3k");

        assertThat(html.get(), containsString("Hospitality Platform ALICE"));
    }
}
