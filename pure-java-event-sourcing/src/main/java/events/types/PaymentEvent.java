package events.types;

import domain.OrderId;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class PaymentEvent implements Event {
    private final EventId id = EventId.random();
    private final OrderId orderId;
    private final Instant occurredAt;
    private final EventType type = EventType.PAID;

    public static PaymentEvent of(OrderId orderId) {
        return PaymentEvent.builder()
                .orderId(orderId)
                .occurredAt(Instant.now())
                .build();
    }

}
