package com.muatik.eventsourcingwithspringkafka.events;

import com.muatik.eventsourcingwithspringkafka.domain.Order;
import com.muatik.eventsourcingwithspringkafka.domain.OrderStatus;
import com.muatik.eventsourcingwithspringkafka.events.types.Event;

import java.util.List;

import static com.muatik.eventsourcingwithspringkafka.domain.OrderStatus.ORDER_CLOSED;
import static com.muatik.eventsourcingwithspringkafka.domain.OrderStatus.ORDER_PAID;
import static com.muatik.eventsourcingwithspringkafka.domain.OrderStatus.ORDER_REFUNDED;
import static com.muatik.eventsourcingwithspringkafka.domain.OrderStatus.ORDER_RETURNED;
import static com.muatik.eventsourcingwithspringkafka.domain.OrderStatus.ORDER_SHIPPED;
import static java.util.Arrays.asList;

class OrderAggregator {

    static Order process(Order order, Event event) {
        switch (order.getState()) {
            case ORDER_CREATED:
                return makeOrderChange(order, event, asList(ORDER_PAID, ORDER_CLOSED));
            case ORDER_PAID:
                return makeOrderChange(order, event, asList(ORDER_SHIPPED, ORDER_REFUNDED));
            case ORDER_SHIPPED:
                return makeOrderChange(order, event, asList(ORDER_RETURNED, ORDER_CLOSED));
            case ORDER_RETURNED:
                return makeOrderChange(order, event, asList(ORDER_REFUNDED));
            case ORDER_REFUNDED:
                return makeOrderChange(order, event, asList(ORDER_PAID, ORDER_CLOSED));
            case ORDER_CLOSED:
                throw new IllegalArgumentException("closed order cannot be changed");
            default:
                throw new IllegalArgumentException("unknown new order state");
        }
    }

    private static Order makeOrderChange(Order order, Event event, List<OrderStatus> allowed) {
        validateTransition(order.getState(), allowed, event.getOrderStatus());
        return order.toBuilder()
                .state(event.getOrderStatus()).build();
    }

    private static void validateTransition(OrderStatus current, List<OrderStatus> allowed, OrderStatus requested) {
        if (!allowed.contains(requested)) {
            throw new IllegalArgumentException("order state transition from " + current + " to " + requested + " not allowed");
        }
    }
}
