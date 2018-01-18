package de.metro.code.warehouse.orderservice.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import de.metro.code.warehouse.orderservice.persistence.model.Order;

public interface OrderRepository {

    Order save(Order order);

    List<Order> findAll();

    List<Order> findRecentOrders(LocalDateTime localDateTime);

    Optional<Order> getOrderById(String orderId);
}