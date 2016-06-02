package com.example.twitter.lemmatizer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by frandayz on 02.06.16.
 */
@RunWith(JUnit4.class)
public class LemmatizerTest {

    private LemmatizerService lemmatizerService = new LemmatizerService();

    @Test
    public void shouldLemmatizePluralAsSingular() {
        String input = "technology technologies";
        List<String> lemmas = lemmatizerService.lemmatize(input);
        assertThat(lemmas, hasItems("technology", "technology"));
    }

    @Test
    public void shouldLemmatizeVerbFormsAsInfinitive() {
        String input = "developed developer";
        List<String> lemmas = lemmatizerService.lemmatize(input);
        assertThat(lemmas, hasItems("develop", "develop"));
    }
}
