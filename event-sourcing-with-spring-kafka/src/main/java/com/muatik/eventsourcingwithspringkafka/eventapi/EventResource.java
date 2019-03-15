package com.muatik.eventsourcingwithspringkafka.eventapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.muatik.eventsourcingwithspringkafka.domain.OrderId;
import com.muatik.eventsourcingwithspringkafka.events.Processor;
import com.muatik.eventsourcingwithspringkafka.events.types.EventId;
import com.muatik.eventsourcingwithspringkafka.events.types.OrderCreateEvent;
import com.muatik.eventsourcingwithspringkafka.events.types.OrderPayEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/events")
public class EventResource {

    private final Processor processor;
    private EventRepository eventRepository;
    private final OrderEventProducer producer;

    public EventResource(OrderEventProducer producer, Processor processor, EventRepository eventRepository) {
        this.producer = producer;
        this.processor = processor;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/test-fire")
    public void fire() throws JsonProcessingException {
        final OrderCreateEvent orderCreateEvent = OrderCreateEvent.builder()
                .id(EventId.random())
                .orderId(OrderId.random())
                .amount(100)
                .createdAt(Instant.now()).build();

        processor.createOrder(orderCreateEvent);

        final OrderPayEvent orderPayEvent = OrderPayEvent.builder()
                .orderId(orderCreateEvent.getOrderId())
                .id(EventId.random())
                .build();

        producer.publish(orderPayEvent);
    }

    @GetMapping("/")
    public Iterable<KafkaEvent> getEvents() {
        return eventRepository.findAll();
    }
}
