package de.metro.code.warehouse.orderservice.persistence;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import de.metro.code.warehouse.orderservice.persistence.model.Article;

@Service
public class ArticleRepositoryImpl implements ArticleRepository {

    private static final Logger log = LoggerFactory.getLogger(ArticleRepositoryImpl.class);

    private final Random random = new Random();

    private Map<String, Article> articles = new HashMap<>();

    @Override
    public Article save(final Article article) {
        final Article newArticle = new Article(article);
        articles.put(article.getId(), newArticle);
        log.info("article saved: " + newArticle);
        return newArticle;
    }

    @Override
    public List<Article> findAll() {
        return articles.values().stream().map(Article::new).collect(Collectors.toList());
    }

    @Override
    public Optional<Article> getRandomArticle() {
        if (articles.isEmpty()) {
            return Optional.empty();
        }

        final int randomIndex = random.nextInt(articles.size());
        final Iterator<Article> iterator = articles.values().iterator();

        for (int i = 0; i < randomIndex; i++) {
            iterator.next();
        }

        return Optional.of(new Article(iterator.next()));
    }

    @Override
    public int getSize() {
        return articles.size();
    }
}
