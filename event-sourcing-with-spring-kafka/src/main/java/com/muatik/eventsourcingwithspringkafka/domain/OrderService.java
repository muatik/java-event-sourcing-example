package com.muatik.eventsourcingwithspringkafka.domain;

import org.springframework.stereotype.Service;

import java.util.List;

import static com.muatik.eventsourcingwithspringkafka.domain.OrderStatus.ORDER_CLOSED;
import static com.muatik.eventsourcingwithspringkafka.domain.OrderStatus.ORDER_PAID;
import static com.muatik.eventsourcingwithspringkafka.domain.OrderStatus.ORDER_REFUNDED;
import static com.muatik.eventsourcingwithspringkafka.domain.OrderStatus.ORDER_RETURNED;
import static com.muatik.eventsourcingwithspringkafka.domain.OrderStatus.ORDER_SHIPPED;
import static java.util.Arrays.asList;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order process(Order order, OrderChangeRequest request) {
        switch (request.getState()) {
            case ORDER_CREATED:
                return makeOrderChange(order, request, asList(ORDER_PAID, ORDER_CLOSED));
            case ORDER_PAID:
                return makeOrderChange(order, request, asList(ORDER_SHIPPED));
            case ORDER_SHIPPED:
                return makeOrderChange(order, request, asList(ORDER_RETURNED, ORDER_CLOSED));
            case ORDER_RETURNED:
                return makeOrderChange(order, request, asList(ORDER_REFUNDED));
            case ORDER_REFUNDED:
                return makeOrderChange(order, request, asList(ORDER_PAID, ORDER_CLOSED));
            case ORDER_CLOSED:
                throw new IllegalArgumentException("closed order cannot be changed");
            default:
                throw new IllegalArgumentException("unknown new order state");
        }
    }

    private Order makeOrderChange(Order order, OrderChangeRequest request, List<OrderStatus> allowed) {
        validateTransition(order.getState(), asList(ORDER_SHIPPED), request.getState());
        return order.toBuilder()
                .state(request.getState()).build();
    }

    private void validateTransition(OrderStatus current, List<OrderStatus> allowed, OrderStatus requested) {
        if (!allowed.contains(requested)) {
            throw new IllegalArgumentException("order state transition from " + current + " to " + requested + " not allowed");
        }
    }


}
