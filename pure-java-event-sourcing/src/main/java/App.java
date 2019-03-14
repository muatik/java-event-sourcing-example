import domain.OrderId;
import domain.OrderRepository;
import events.EventProcessor;
import events.EventRepository;
import events.types.CloseEvent;
import events.types.CreateEvent;
import events.types.PaymentEvent;
import events.types.RefundEvent;

import java.util.stream.Stream;

public class App {
    public static void main(String[] args) {
        final OrderRepository orderRepository = new OrderRepository();
        final EventRepository eventRepository = new EventRepository();
        final EventProcessor eventProcessor = new EventProcessor(orderRepository, eventRepository);
        final OrderId orderId = OrderId.of("my-order");

        eventProcessor.processCreateEvent(CreateEvent.of(orderId, 10000));
        Stream.of(
                PaymentEvent.of(orderId),
                RefundEvent.of(orderId),
                CloseEvent.of(orderId)
        ).forEach(eventProcessor::process);

        System.out.println(orderRepository.findById(orderId));
        eventRepository.findByOrderId(orderId).forEach(System.out::println);
    }

}
