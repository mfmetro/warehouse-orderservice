package de.metro.code.warehouse.orderservice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.metro.code.warehouse.orderservice.persistence.OrderRepository;
import de.metro.code.warehouse.orderservice.persistence.model.Order;

@RestController
public class OrdersController {

    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping(path = "/order/{id}", method = RequestMethod.GET)
    public @ResponseBody Order getOrder(final @PathVariable("id") String orderId) {
        return orderRepository.getOrderById(orderId).orElseThrow(() -> new ResourceNotFoundException());
    }

    @RequestMapping(path = "/orderFeed", method = RequestMethod.GET)
    public @ResponseBody List<String> ordersFeed(
            final @RequestParam("date") String dateString,
            final @RequestParam(name = "time", defaultValue = "00:00") String timeString) {

        final LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
        final LocalTime time = LocalTime.parse(timeString, DateTimeFormatter.ISO_TIME);

        final LocalDateTime pointInTime = LocalDateTime.of(date, time);

        return orderRepository.findRecentOrders(pointInTime).stream()
                .map(Order::getId)
                .collect(Collectors.toList());
    }
}
