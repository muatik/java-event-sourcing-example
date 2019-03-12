package domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import utils.RepositoryEntry;

import java.time.Instant;


@Getter
@ToString
@Builder(toBuilder = true)
public class Order implements RepositoryEntry<OrderId> {
    final private OrderId id;
    final private OrderStatus state;
    final private long amount;
    final private Instant createdAt;
    final private Instant updatedAt;
}
