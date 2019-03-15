package com.muatik.eventsourcingwithspringkafka.eventapi;

import com.muatik.eventsourcingwithspringkafka.domain.OrderId;
import com.muatik.eventsourcingwithspringkafka.domain.OrderStatus;
import com.muatik.eventsourcingwithspringkafka.events.types.Event;
import com.muatik.eventsourcingwithspringkafka.events.types.EventId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
class KafkaEvent implements Event, Serializable {
    @EmbeddedId
    private final EventId id;
    private final OrderId orderId;
    private final Instant updatedAt;
    private final OrderStatus orderStatus = OrderStatus.ORDER_PAID;
}
