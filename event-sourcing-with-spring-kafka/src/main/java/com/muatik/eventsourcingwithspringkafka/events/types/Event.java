package com.muatik.eventsourcingwithspringkafka.events.types;

import com.muatik.eventsourcingwithspringkafka.domain.OrderId;
import com.muatik.eventsourcingwithspringkafka.domain.OrderStatus;

import java.time.Instant;

public interface Event{
    EventId getId();
    OrderId getOrderId();
    Instant getUpdatedAt();
    OrderStatus getOrderStatus();
}
