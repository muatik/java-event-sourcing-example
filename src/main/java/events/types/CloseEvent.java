package events.types;

import domain.OrderId;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class CloseEvent implements Event {
    private final EventId id = EventId.random();
    private final OrderId orderId;
    private final Instant occurredAt;
    private final EventType type = EventType.CLOSED;

    public static CloseEvent of(OrderId orderId) {
        return CloseEvent.builder()
                .orderId(orderId)
                .occurredAt(Instant.now())
                .build();
    }
}
