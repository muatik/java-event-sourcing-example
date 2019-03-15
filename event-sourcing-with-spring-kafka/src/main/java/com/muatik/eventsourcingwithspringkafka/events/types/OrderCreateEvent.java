package com.muatik.eventsourcingwithspringkafka.events.types;

import com.muatik.eventsourcingwithspringkafka.domain.OrderId;
import com.muatik.eventsourcingwithspringkafka.domain.OrderStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
@EqualsAndHashCode(of = "id")
public class OrderCreateEvent implements Event{
    private final EventId id;
    private final OrderId orderId;
    private final long amount;
    private final Instant createdAt;
    private final OrderStatus orderStatus;

    @Override
    public Instant getUpdatedAt() {
        return createdAt;
    }
}
