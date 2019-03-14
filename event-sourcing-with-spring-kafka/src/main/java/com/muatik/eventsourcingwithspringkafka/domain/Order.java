package com.muatik.eventsourcingwithspringkafka.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Entity;
import java.time.Instant;

@Entity
@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
public class Order {
    private final OrderId id;
    private final long amount;
    private final OrderStatus state;
    private final Instant createdAt;
    private final Instant updatedAt;
}
