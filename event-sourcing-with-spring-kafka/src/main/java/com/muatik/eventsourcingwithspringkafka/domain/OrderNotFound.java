package com.muatik.eventsourcingwithspringkafka.domain;

public class OrderNotFound extends RuntimeException {
    public OrderNotFound(OrderId orderId) {
        super("Order not found " + orderId);
    }
}
