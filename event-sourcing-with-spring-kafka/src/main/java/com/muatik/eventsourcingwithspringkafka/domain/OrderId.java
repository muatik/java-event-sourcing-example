package com.muatik.eventsourcingwithspringkafka.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public class OrderId implements Serializable {

    private final UUID value;

    // because of JPA
    public OrderId() {
        value = UUID.randomUUID();
    }

    OrderId(UUID value) {
        this.value = value;
    }

    public static OrderId of(String value) {
        return new OrderId(UUID.fromString(value));
    }

    public static OrderId random() {
        return new OrderId(UUID.randomUUID());
    }
}
