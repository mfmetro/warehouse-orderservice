package de.metro.code.warehouse.orderservice.components;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.metro.code.warehouse.orderservice.persistence.ArticleRepository;
import de.metro.code.warehouse.orderservice.persistence.OrderRepository;
import de.metro.code.warehouse.orderservice.persistence.model.Article;
import de.metro.code.warehouse.orderservice.persistence.model.Order;
import de.metro.code.warehouse.orderservice.persistence.model.OrderItem;

@Component
public class OrderGenerator {

    private static final Logger log = LoggerFactory.getLogger(OrderGenerator.class);

    private static final int MAX_DELIVERY_DATE_DELTA_IN_DAYS = 3;
    private static final int MAX_NUMBER_OF_ARTICLES = 5;
    private static final int MAX_QUANTITY = 20;

    private final Random random = new Random();

    @Value(value = "${orderGenerator.probability:0.05}")
    private double probability;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Scheduled(initialDelayString = "${orderGenerator.initialDelay:10000}", fixedRateString = "${orderGenerator.fixedRate:1000}")
    public void createRandomOrder() {
        if (random.nextDouble() < probability) {
            final int numberOfArticles = random.nextInt(MAX_NUMBER_OF_ARTICLES) + 1;
            final Order order = createOrder(numberOfArticles);
            orderRepository.save(order);
            log.info("new order generated: " + order);
        }
    }

    private Order createOrder(final int numberOfArticles) {
        final Set<Article> articles = getRandomArticles(numberOfArticles);
        final List<OrderItem> orderItems = articles.stream()
            .map(article -> OrderItem.builder()
                .withArticle(article)
                .withQuantity(random.nextInt(MAX_QUANTITY) + 1)
                .build())
            .collect(Collectors.toList());

        final int deliveryDateDeltaDays = random.nextInt(MAX_DELIVERY_DATE_DELTA_IN_DAYS) + 1;
        final LocalDate deliveryDate = LocalDate.now().plusDays(deliveryDateDeltaDays);

        return Order.builder()
            .withId(UUID.randomUUID().toString())
            .withCustomerId(UUID.randomUUID().toString())
            .withCreationTime(LocalDateTime.now())
            .withDeliveryDate(deliveryDate)
            .withOrderItems(orderItems)
            .build();
    }

    private Set<Article> getRandomArticles(final int numberOfArticles) {
        final int repositorySize = articleRepository.getSize();
        if (numberOfArticles > repositorySize) {
            throw new RuntimeException("unable to select " + numberOfArticles + " out of " + repositorySize);
        }

        final Set<Article> articles = new HashSet<>();

        do {
            final Article article = articleRepository
                .getRandomArticle()
                .orElseThrow(() -> new RuntimeException("no articles present in article repository"));

            articles.add(article);
        } while (articles.size() < numberOfArticles);

        return articles;
    }
}
