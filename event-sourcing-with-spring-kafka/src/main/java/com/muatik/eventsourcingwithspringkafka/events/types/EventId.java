package com.muatik.eventsourcingwithspringkafka.events.types;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor(force = true)
public class EventId implements Serializable {
    private final UUID value;

    public EventId(UUID value) {
        this.value = value;
    }

    public static EventId of(String value) {
        return new EventId(UUID.fromString(value));
    }

    public static EventId random() {
        return new EventId(UUID.randomUUID());
    }

}
