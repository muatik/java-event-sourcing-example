package com.muatik.eventsourcingwithspringkafka.events;

import com.muatik.eventsourcingwithspringkafka.domain.Order;
import com.muatik.eventsourcingwithspringkafka.domain.OrderId;
import com.muatik.eventsourcingwithspringkafka.domain.OrderStatus;
import com.muatik.eventsourcingwithspringkafka.events.types.Event;
import com.muatik.eventsourcingwithspringkafka.events.types.EventId;
import com.muatik.eventsourcingwithspringkafka.events.types.OrderCloseEvent;
import com.muatik.eventsourcingwithspringkafka.events.types.OrderPayEvent;
import com.muatik.eventsourcingwithspringkafka.events.types.OrderRefundEvent;
import com.muatik.eventsourcingwithspringkafka.events.types.OrderReturnEvent;
import com.muatik.eventsourcingwithspringkafka.events.types.OrderShipEvent;
import org.junit.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderAggregatorTest {

    private OrderId orderId = OrderId.random();

    private Order orderCreated = getOrderWithState(orderId, OrderStatus.ORDER_CREATED);
    private Order orderPaid = getOrderWithState(orderId, OrderStatus.ORDER_PAID);
    private Order orderShipped = getOrderWithState(orderId, OrderStatus.ORDER_SHIPPED);
    private Order orderReturned = getOrderWithState(orderId, OrderStatus.ORDER_RETURNED);
    private Order orderRefunded = getOrderWithState(orderId, OrderStatus.ORDER_REFUNDED);
    private Order orderClosed = getOrderWithState(orderId, OrderStatus.ORDER_CLOSED);

    final OrderPayEvent orderPayEvent = OrderPayEvent.builder()
            .id(EventId.random())
            .orderId(orderId)
            .updatedAt(Instant.now()).build();

    final OrderShipEvent orderShipEvent = OrderShipEvent.builder()
            .id(EventId.random()).orderId(orderId).updatedAt(Instant.now()).build();

    final OrderReturnEvent orderReturnEvent = OrderReturnEvent.builder()
            .id(EventId.random()).orderId(orderId).updatedAt(Instant.now()).build();

    final OrderRefundEvent orderRefundEvent = OrderRefundEvent.builder()
            .id(EventId.random()).orderId(orderId).updatedAt(Instant.now()).build();

    final OrderCloseEvent orderCloseEvent = OrderCloseEvent.builder()
            .id(EventId.random()).orderId(orderId).updatedAt(Instant.now()).build();

    final List<Event> events = Arrays.asList(orderPayEvent, orderShipEvent, orderReturnEvent, orderRefundEvent, orderCloseEvent);

    private static Order getOrderWithState(OrderId orderId, OrderStatus status) {
        return Order.builder()
                .id(orderId)
                .amount(100)
                .state(status)
                .createdAt(Instant.now())
                .updatedAt(Instant.now()).build();
    }

    @Test
    public void shouldMakeTransitionFromCreated() {
        OrderAggregator.process(orderCreated, orderPayEvent);
        OrderAggregator.process(orderCreated, orderCloseEvent);
        events.stream()
                .filter(e-> !List.of(orderPayEvent, orderCloseEvent).contains(e))
                .forEach(e-> assertThatThrownBy(()->OrderAggregator.process(orderCreated, e)));
    }

    @Test
    public void shouldMakeTransitionFromPaid() {
        OrderAggregator.process(orderPaid, orderShipEvent);
        OrderAggregator.process(orderPaid, orderRefundEvent);
        events.stream()
                .filter(e-> !List.of(orderShipEvent, orderRefundEvent).contains(e))
                .forEach(e-> assertThatThrownBy(()->OrderAggregator.process(orderPaid, e)));
    }

    @Test
    public void shouldMakeTransitionFromShipped() {
        OrderAggregator.process(orderShipped, orderReturnEvent);
        OrderAggregator.process(orderShipped, orderCloseEvent);
        events.stream()
                .filter(e-> !List.of(orderReturnEvent, orderCloseEvent).contains(e))
                .forEach(e-> assertThatThrownBy(()->OrderAggregator.process(orderShipped, e)));
    }

    @Test
    public void shouldMakeTransitionFromReturned() {
        OrderAggregator.process(orderReturned, orderRefundEvent);
        events.stream()
                .filter(e-> !e.equals(orderRefundEvent))
                .forEach(e-> assertThatThrownBy(()->OrderAggregator.process(orderReturned, e)));
    }

    @Test
    public void shouldMakeTransitionFromRefunded() {
        OrderAggregator.process(orderRefunded, orderPayEvent);
        OrderAggregator.process(orderRefunded, orderCloseEvent);
        events.stream()
                .filter(e-> !List.of(orderPayEvent, orderCloseEvent).contains(e))
                .forEach(e-> assertThatThrownBy(()->OrderAggregator.process(orderRefunded, e)));
    }

    @Test
    public void shouldMakeTransitionFromClosed() {
        events.forEach(e-> assertThatThrownBy(()->OrderAggregator.process(orderClosed, e)));
    }

}