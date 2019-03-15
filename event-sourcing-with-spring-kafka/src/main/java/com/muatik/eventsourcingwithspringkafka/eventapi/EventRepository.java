package com.muatik.eventsourcingwithspringkafka.eventapi;

import com.muatik.eventsourcingwithspringkafka.events.types.EventId;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<KafkaEvent, EventId> {
}
