package com.muatik.eventsourcingwithspringkafka.events;

import com.muatik.eventsourcingwithspringkafka.domain.Order;
import com.muatik.eventsourcingwithspringkafka.domain.OrderId;
import com.muatik.eventsourcingwithspringkafka.domain.OrderRepository;
import com.muatik.eventsourcingwithspringkafka.domain.OrderStatus;
import com.muatik.eventsourcingwithspringkafka.events.types.EventId;
import com.muatik.eventsourcingwithspringkafka.events.types.OrderCreateEvent;
import com.muatik.eventsourcingwithspringkafka.events.types.OrderPayEvent;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProcessorTest {

    @Mock
    OrderRepository orderRepository = mock(OrderRepository.class);

    Processor processor = new Processor(orderRepository);

    Order order = Order.builder()
            .id(OrderId.random())
            .state(OrderStatus.ORDER_CREATED)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .amount(100).build();

    @Test
    public void shouldCreateOrderFromEvent() {
        final OrderCreateEvent orderCreateEvent = OrderCreateEvent.builder()
                .amount(order.getAmount())
                .id(EventId.random())
                .orderId(order.getId())
                .createdAt(order.getCreatedAt()).build();

        processor.createOrder(orderCreateEvent);

        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository, times(1)).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(order);
    }

    @Test
    public void shouldProcessEvents() {

        final OrderPayEvent payEvent = OrderPayEvent.builder()
                .id(EventId.random())
                .orderId(order.getId())
                .updatedAt(Instant.now()).build();

        when(orderRepository.findById(order.getId())).thenReturn(Optional.ofNullable(order));
        when(orderRepository.save(any())).thenReturn(order);
        final Order newOrder = processor.process(payEvent);

        ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository, times(1)).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getId()).isEqualTo(order.getId());
        assertThat(argumentCaptor.getValue().getState()).isEqualTo(OrderStatus.ORDER_PAID);
    }

}