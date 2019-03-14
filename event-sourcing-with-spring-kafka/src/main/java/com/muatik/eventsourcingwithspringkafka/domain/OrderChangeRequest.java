package com.muatik.eventsourcingwithspringkafka.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class OrderChangeRequest {
    private final OrderStatus state;
    private final Instant updateAt;
}
