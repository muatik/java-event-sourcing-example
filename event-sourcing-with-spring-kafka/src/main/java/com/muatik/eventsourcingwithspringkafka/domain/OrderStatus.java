package com.muatik.eventsourcingwithspringkafka.domain;

public enum OrderStatus {
    ORDER_CREATED,
    ORDER_PAID,
    ORDER_SHIPPED,
    ORDER_RETURNED,
    ORDER_REFUNDED,
    ORDER_CLOSED
}
