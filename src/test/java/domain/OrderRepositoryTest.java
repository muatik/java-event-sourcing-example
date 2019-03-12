package domain;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderRepositoryTest {

    private OrderId orderId;
    private Order orderV1;
    private OrderRepository orderRepository;

    @Before
    public void setup() {
        orderId = OrderId.of("my-order");
        orderV1 = Order.builder()
                .id(orderId)
                .version(1)
                .build();
        orderRepository = new OrderRepository();
    }

    @Test
    public void shouldThrowOptimisticLockExceptionWhen() {
        orderRepository.save(orderV1);

        assertThatThrownBy(() -> orderRepository.save(orderV1))
                .hasMessage("optimistic lock exception. the given order has became stale");
    }

    @Test
    public void shouldPersistOrderWithNewVersion() {
        final long newVersion = orderV1.getVersion() + 1;
        final Order orderV2 = orderV1.toBuilder().version(newVersion).build();
        orderRepository.save(orderV2);

        assertThat(orderRepository.findById(orderV1.getId()))
                .isPresent()
                .get()
                .extracting(Order::getVersion)
                .isEqualTo(newVersion);
    }
}