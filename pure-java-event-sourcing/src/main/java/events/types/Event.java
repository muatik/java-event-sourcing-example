package events.types;

import domain.OrderId;
import utils.RepositoryEntry;

import java.time.Instant;

public interface Event extends RepositoryEntry<EventId> {
    EventId getId();
    OrderId getOrderId();
    Instant getOccurredAt();
    EventType getType();


}
