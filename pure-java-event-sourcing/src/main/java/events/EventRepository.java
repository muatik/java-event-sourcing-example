package events;

import domain.OrderId;
import events.types.Event;
import events.types.EventId;
import utils.Repository;

import java.util.List;
import java.util.stream.Collectors;

public class EventRepository extends Repository<EventId, Event> {
    public List<Event> findByOrderId(OrderId orderId) {
        return entries.values().stream()
                .filter(o-> o.getOrderId().equals(orderId))
                .collect(Collectors.toList());
    }
}
