package com.example.twitter.lemmatizer;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by frandayz on 31.05.16.
 */
@Service
public class LemmatizerService {

    public List<String> lemmatize(String text) {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props, false);
        Annotation document = pipeline.process(text);

        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        List<String> lemmas = sentences.stream()
                .map(s -> s.get(TokensAnnotation.class))
                .flatMap(Collection::stream)
                .map(tk -> tk.get(LemmaAnnotation.class))
                .collect(Collectors.toList());

        return lemmas;
    }
}
