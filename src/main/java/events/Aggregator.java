package events;

import domain.Order;
import domain.OrderStatus;
import events.types.CreateEvent;
import events.types.Event;
import events.types.EventType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static events.types.EventType.CLOSED;
import static events.types.EventType.PAID;
import static events.types.EventType.REFUNDED;
import static events.types.EventType.SHIPPED;

class Aggregator {

    static Order createNewEvent(CreateEvent event) {
        return Order.builder()
                .id(event.getOrderId())
                .amount(event.getOrderAmount())
                .createdAt(event.getOccurredAt())
                .updatedAt(event.getOccurredAt())
                .state(OrderStatus.CREATED).build();

    }

    static Order apply(Order order, Event event) {
        switch (order.getState()) {
            case CREATED:
                validateTransition(order.getState(), Arrays.asList(PAID, CLOSED), event.getType());
                return buildOrder(order, event);
            case PAID:
                validateTransition(order.getState(), Arrays.asList(SHIPPED, REFUNDED), event.getType());
                return buildOrder(order, event);
            case SHIPPED:
                validateTransition(order.getState(), Arrays.asList(REFUNDED, CLOSED), event.getType());
                return buildOrder(order, event);
            case REFUNDED:
                validateTransition(order.getState(), Arrays.asList(PAID, CLOSED), event.getType());
                return buildOrder(order, event);
            case CLOSED:
                validateTransition(order.getState(), Collections.emptyList(), event.getType());
            default:
                throw new IllegalArgumentException("unknown event type: " + event.getType());
        }
    }

    private static Order buildOrder(Order order, Event event) {
        return order.toBuilder()
                .state(event.getType().toOrderStatus())
                .updatedAt(event.getOccurredAt())
                .build();
    }

    private static void validateTransition(OrderStatus current, List<EventType> allowed, EventType intented) {
        if (!allowed.contains(intented)) {
            throw new IllegalArgumentException(String.format("transition from %s to %s not allowed", current, intented));
        }
    }
}
