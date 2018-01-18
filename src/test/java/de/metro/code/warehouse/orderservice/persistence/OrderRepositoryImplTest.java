package de.metro.code.warehouse.orderservice.persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metro.code.warehouse.orderservice.persistence.model.Article;
import de.metro.code.warehouse.orderservice.persistence.model.Order;
import de.metro.code.warehouse.orderservice.persistence.model.OrderItem;
import de.metro.code.warehouse.orderservice.persistence.model.StorageArea;

public class OrderRepositoryImplTest {

    private static final int DEFAULT_ORDER_ITEM_QUANTITY = 2;
    private static final String DEFAULT_ARTICLE_NAME = "Sample Article";
    private static final StorageArea DEFAULT_ARTICLE_STORAGE_AREA = StorageArea.MAIN;

    private OrderRepository orderRepository;

    @Before
    public void initOrderRepository() {
        orderRepository = new OrderRepositoryImpl();
    }

    @Test
    public void testGetOrderByIdNullReturnsEmptyOptional() {
        final Optional<Order> optionalOrder = orderRepository.getOrderById(null);
        Assert.assertFalse(optionalOrder.isPresent());
    }

    @Test
    public void testGetOrderByUnknownIdReturnsEmptyOptional() {
        final String unknownOrderId = UUID.randomUUID().toString();
        final Optional<Order> optionalOrder = orderRepository.getOrderById(unknownOrderId);
        Assert.assertFalse(optionalOrder.isPresent());
    }

    @Test
    public void testGetOrderByKnownIdReturnsValue() {
        final String orderId = UUID.randomUUID().toString();
        final Order order = createOrder(orderId);
        orderRepository.save(order);

        final Optional<Order> optionalOrder = orderRepository.getOrderById(orderId);

        final Order retrievedOrder = optionalOrder.get();
        Assert.assertFalse(order == retrievedOrder);
        Assert.assertEquals(order, retrievedOrder);
    }

    @Test
    public void testFindAllWithoutSaveReturnsEmptyList() {
        final List<Order> orders = orderRepository.findAll();
        Assert.assertTrue(orders.isEmpty());
    }

    private Order createOrder(final String orderId) {
        final OrderItem orderItem = createOrderItem();

        return Order.builder()
            .withId(orderId)
            .withCreationTime(LocalDateTime.now())
            .withCustomerId(UUID.randomUUID().toString())
            .withDeliveryDate(tomorrow())
            .withOrderItems(Arrays.asList(orderItem))
            .build();
    }

    private OrderItem createOrderItem() {
        final Article article = createArticle();

        return OrderItem.builder()
            .withArticle(article)
            .withQuantity(DEFAULT_ORDER_ITEM_QUANTITY)
            .build();
    }

    private Article createArticle() {
        return Article.builder()
            .withEan(UUID.randomUUID().toString())
            .withId(UUID.randomUUID().toString())
            .withName(DEFAULT_ARTICLE_NAME)
            .withStorageArea(DEFAULT_ARTICLE_STORAGE_AREA)
            .build();
    }

    private LocalDate tomorrow() {
        return LocalDate.now().plusDays(1);
    }

}
