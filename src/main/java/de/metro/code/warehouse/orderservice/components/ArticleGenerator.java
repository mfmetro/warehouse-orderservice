package de.metro.code.warehouse.orderservice.components;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.metro.code.warehouse.orderservice.persistence.ArticleRepository;
import de.metro.code.warehouse.orderservice.persistence.model.Article;

@Component
public class ArticleGenerator implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(ArticleGenerator.class);

    private static final String ARTICLES_JSON_PATH = "classpath:articles.json";

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent applicationReadyEvent) {
        try {
            final InputStream in = resourceLoader.getResource(ARTICLES_JSON_PATH).getInputStream();
            final List<Article> articles = objectMapper.readValue(in, new TypeReference<List<Article>>() {});
            articles.stream().forEach(articleRepository::save);
        } catch (final Exception e) {
            log.error("unable to read articles source file", e);
        }
    }
}