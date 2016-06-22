package com.example.twitter.scrapper;

import com.example.twitter.scrapper.HttpService;
import com.example.twitter.scrapper.ScrapperService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by frandayz on 02.06.16.
 */
@RunWith(JUnit4.class)
public class ScrapperServiceTest {

    private ScrapperService scrapperService =
            new ScrapperService(new HttpService());

    @Test
    public void shouldExtractAllTextFromParagraphs_GivenHtmlBody() {
        String html = "" +
                "<html>" +
                "<body>" +
                "<p>Text1</p>" +
                "<p><span>Text2</span></p>" +
                "<p>Text3</p>" +
                "</body>" +
                "</html>";

        Optional<String> words = scrapperService.scrap(html);
        assertThat(words.get(), is("Text1 Text2 Text3"));
    }

    @Test
    public void shouldExtractEmpty_GivenEmptyString() {
        String html = "";
        Optional<String> words = scrapperService.scrap(html);
        assertThat(words, is(Optional.empty()));
    }
}
