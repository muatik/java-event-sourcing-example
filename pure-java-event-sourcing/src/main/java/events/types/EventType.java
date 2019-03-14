package events.types;

import domain.OrderStatus;

public enum EventType {
    CREATED,
    PAID,
    REFUNDED,
    SHIPPED,
    CANCELLED,
    CLOSED;

    public OrderStatus toOrderStatus() {
        switch (this) {
            case CREATED:
                return OrderStatus.CREATED;
            case PAID:
                return OrderStatus.PAID;
            case SHIPPED:
                return OrderStatus.SHIPPED;
            case CANCELLED:
                return OrderStatus.CANCELLED;
            case CLOSED:
                return OrderStatus.CLOSED;
            case REFUNDED:
                return OrderStatus.REFUNDED;
            default:
                throw new IllegalArgumentException("unknown event type " + this);
        }
    }
}
