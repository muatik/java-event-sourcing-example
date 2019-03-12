package events.types;

import domain.OrderId;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class CreateEvent implements Event{
    private final EventId id = EventId.random();
    private final OrderId orderId;
    private final Instant occurredAt;
    private final EventType type = EventType.CREATED;
    private final long orderAmount;

    public static CreateEvent of(OrderId orderId, long orderAmount) {
        return CreateEvent.builder()
                .orderId(orderId)
                .occurredAt(Instant.now())
                .orderAmount(orderAmount).
                build();
    }

}
