package de.metro.code.warehouse.orderservice.persistence.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.common.base.MoreObjects;

public class Order {

    private String id;
    private String customerId;
    private LocalDate deliveryDate;
    private LocalDateTime creationTime;
    private List<OrderItem> orderItems;

    protected Order() {
    }

    public Order(final Order order) {
        this.id = order.id;
        this.customerId = order.customerId;
        this.deliveryDate = order.deliveryDate;
        this.creationTime = order.creationTime;
        this.orderItems = order.orderItems.stream()
            .map(OrderItem::new)
            .collect(Collectors.toList());
    }

    private Order(final Builder builder) {
        this.id = builder.id;
        this.customerId = builder.customerId;
        this.deliveryDate = builder.deliveryDate;
        this.creationTime = builder.creationTime;
        this.orderItems = builder.orderItems;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(final LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(final LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(final List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, deliveryDate, creationTime, orderItems);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Order)) {
            return false;
        }

        final Order other = (Order) obj;
        return Objects.equals(id, other.id) &&
            Objects.equals(customerId, other.customerId) &&
            Objects.equals(deliveryDate, other.deliveryDate) &&
            Objects.equals(creationTime, other.creationTime) &&
            Objects.equals(orderItems, other.orderItems);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("customerId", customerId)
            .add("deliveryDate", deliveryDate)
            .add("creationTime", creationTime)
            .add("orderItems", orderItems)
            .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String id;
        private String customerId;
        private LocalDate deliveryDate;
        private LocalDateTime creationTime;
        private List<OrderItem> orderItems;

        public Builder withId(final String id) {
            this.id = id;
            return this;
        }

        public Builder withCustomerId(final String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder withDeliveryDate(final LocalDate deliveryDate) {
            this.deliveryDate = deliveryDate;
            return this;
        }

        public Builder withCreationTime(final LocalDateTime creationTime) {
            this.creationTime = creationTime;
            return this;
        }

        public Builder withOrderItems(final List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

}