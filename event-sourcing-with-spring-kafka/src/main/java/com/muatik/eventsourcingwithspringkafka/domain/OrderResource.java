package com.muatik.eventsourcingwithspringkafka.domain;

import com.muatik.eventsourcingwithspringkafka.domain.Order;
import com.muatik.eventsourcingwithspringkafka.domain.OrderId;
import com.muatik.eventsourcingwithspringkafka.domain.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/")
public class OrderResource {
    private final OrderRepository orderRepository;

    public OrderResource(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/")
    public Iterable<Order> getOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable OrderId orderId) {
        return orderRepository.findById(orderId)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }
}
