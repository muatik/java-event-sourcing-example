package com.muatik.eventsourcingwithspringkafka.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "orders") // 'order' is reserved sql keyword
@Getter
@EqualsAndHashCode(of = "id")
public class Order {
    @Id
    private final OrderId id;
    private final long amount;
    private final OrderStatus state;
    private final Instant createdAt;
    private final Instant updatedAt;

    @Builder(toBuilder = true)
    public Order(OrderId id, long amount, OrderStatus state, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.amount = amount;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Order() { // because of jpa
        this.id = null;
        this.amount = 0;
        this.state = null;
        this.createdAt = null;
        this.updatedAt = null;
    }
}
