package events;

import domain.Order;
import domain.OrderId;
import domain.OrderNotFound;
import domain.OrderRepository;
import events.types.CreateEvent;
import events.types.Event;

public class EventProcessor {
    final private OrderRepository orderRepository;
    private EventRepository eventRepository;

    public EventProcessor(OrderRepository orderRepository, EventRepository eventRepository) {
        this.orderRepository = orderRepository;
        this.eventRepository = eventRepository;
    }

    public void processCreateEvent(CreateEvent createEvent) {
        final Order newOrder = Aggregator.createNewEvent(createEvent);
        eventRepository.save(createEvent);
        orderRepository.save(newOrder);
    }

    public void process(Event event) {
        final OrderId orderId = event.getOrderId();
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFound(orderId));
        Order new_order = Aggregator.apply(order, event);
        eventRepository.save(event);
        orderRepository.save(new_order);
    }
}
