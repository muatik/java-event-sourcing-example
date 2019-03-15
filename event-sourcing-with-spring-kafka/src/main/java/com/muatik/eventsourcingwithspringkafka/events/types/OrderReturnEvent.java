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
public class OrderReturnEvent implements Event {
    private final EventId id;
    private final OrderId orderId;
    private final Instant updatedAt;
    private final OrderStatus orderStatus = OrderStatus.ORDER_RETURNED;
}
