package events.types;

import java.util.UUID;


public class EventId {
    private final String value;

    EventId(String value) {
        this.value = value;
    }

    public static EventId of(UUID uuid) {
        return new EventId(uuid.toString());
    }

    public static EventId random() {
        return of(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value;
    }
}
