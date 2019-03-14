package com.muatik.eventsourcingwithspringkafka.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class OrderId {

    private final String value;

    OrderId(String value) {
        this.value = value;
    }

    private static OrderId of(String value) {
        return new OrderId(value);
    }
}
