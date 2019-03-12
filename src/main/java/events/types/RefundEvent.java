package events.types;

import domain.OrderId;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class RefundEvent implements Event {
    private final EventId id = EventId.random();
    private final OrderId orderId;
    private final Instant occurredAt;
    private final EventType type = EventType.REFUNDED;

    public static RefundEvent of(OrderId orderId) {
        return RefundEvent.builder()
                .orderId(orderId)
                .occurredAt(Instant.now())
                .build();
    }

}
