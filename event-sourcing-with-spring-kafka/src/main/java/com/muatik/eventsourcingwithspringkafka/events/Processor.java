package com.muatik.eventsourcingwithspringkafka.events;

import com.muatik.eventsourcingwithspringkafka.domain.Order;
import com.muatik.eventsourcingwithspringkafka.domain.OrderNotFound;
import com.muatik.eventsourcingwithspringkafka.domain.OrderRepository;
import com.muatik.eventsourcingwithspringkafka.events.types.Event;
import com.muatik.eventsourcingwithspringkafka.events.types.OrderCreateEvent;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.muatik.eventsourcingwithspringkafka.domain.OrderStatus.ORDER_CREATED;

@Component
public class Processor {
    private final OrderRepository orderRepository;

    public Processor(@NonNull OrderRepository orderRepository) {
        this.orderRepository = Objects.requireNonNull(orderRepository);

    }

    public Order createOrder(OrderCreateEvent event) {
        final Order order = Order.builder()
                .id(event.getOrderId())
                .state(ORDER_CREATED)
                .amount(event.getAmount())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getCreatedAt()).build();
        return persistOrder(order);
    }

    public Order process(@NonNull Event event) {
        return orderRepository.findById(event.getOrderId())
                .map(order -> persistOrder(OrderAggregator.process(order, event)))
                .orElseThrow(() -> new OrderNotFound(event.getOrderId()));
    }

    private Order persistOrder(Order order) {
        return orderRepository.save(order);
    }

}
