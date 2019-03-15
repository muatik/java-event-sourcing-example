package com.muatik.eventsourcingwithspringkafka.eventapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muatik.eventsourcingwithspringkafka.events.types.Event;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventProducer {
    private final static String ORDER_EVENTS_TOPIC = "order-events";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OrderEventProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(Event event) throws JsonProcessingException {
        kafkaTemplate.send(ORDER_EVENTS_TOPIC, event.getId().toString(), serialize(event));
    }

    private String serialize(Event event) throws JsonProcessingException {
        return objectMapper.writeValueAsString(event);
    }
}
