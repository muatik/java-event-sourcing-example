package domain;

import utils.Repository;

import java.util.Optional;

public class OrderRepository extends Repository<OrderId, Order> {
    @Override
    public Order save(Order entry) {
        synchronized (entries) {
            final Optional<Order> current = findById(entry.getId());

            if (current.isPresent() && current.get().getVersion() >= entry.getVersion()) {
                throw new RuntimeException("optimistic lock exception. the given order has became stale");
            }

            return super.save(entry);
        }
    }
}
