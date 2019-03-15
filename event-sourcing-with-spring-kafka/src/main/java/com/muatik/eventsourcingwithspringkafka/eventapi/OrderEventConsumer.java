package com.muatik.eventsourcingwithspringkafka.eventapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muatik.eventsourcingwithspringkafka.events.Processor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.IOException;

@Slf4j
@Component
public class OrderEventConsumer {

    private final Processor processor;
    private final ObjectMapper objectMapper;
    private final EventRepository eventRepository;

    public OrderEventConsumer(Processor processor, ObjectMapper objectMapper, EventRepository eventRepository) {
        this.processor = processor;
        this.objectMapper = objectMapper;
        this.eventRepository = eventRepository;
    }

    @KafkaListener(topics = "order-events")
    public void consume(ConsumerRecord<String, String> consumerRecord) {
        try {
            KafkaEvent kafkaEvent = deserialize(consumerRecord.value());
            dispatch(kafkaEvent);
        } catch (IOException e) {
            log.error("cannot deserialize event message to Event class", e);
        }
    }

    @Transactional
    void dispatch(KafkaEvent kafkaEvent) {
        processor.process(kafkaEvent);
        eventRepository.save(kafkaEvent);
    }

    private KafkaEvent deserialize(String payload) throws IOException {
        return objectMapper.readValue(payload, KafkaEvent.class);
    }
}
