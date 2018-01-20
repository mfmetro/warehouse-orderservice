package de.metro.code.warehouse.orderservice.persistence;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.metro.code.warehouse.orderservice.persistence.model.Order;

@Service
public class OrderRepositoryImpl implements OrderRepository {

    private Map<String, Order> orders = new HashMap<>();

    @Override
    public Order save(final Order order) {
        final Order newOrder = new Order(order);
        orders.put(order.getId(), newOrder);
        return newOrder;
    }

    @Override
    public List<Order> findAll() {
        return orders.values().stream().map(Order::new).collect(Collectors.toList());
    }

    @Override
    public List<Order> findRecentOrders(final LocalDateTime pointInTime) {
        return orders.values().stream()
            .filter(order -> !pointInTime.isAfter(order.getCreationTime()))
            .sorted(this::compareOrdersByCreationTime)
            .map(Order::new)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Order> getOrderById(final String orderId) {
        final Order order = orders.get(orderId);

        if (order == null) {
            return Optional.empty();
        } else {
            return Optional.of(new Order(order));
        }
    }

    private int compareOrdersByCreationTime(final Order firstOrder, final Order secondOrder) {
        return firstOrder.getCreationTime().compareTo(secondOrder.getCreationTime());
    }
}
