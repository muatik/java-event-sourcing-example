package domain;

public class OrderNotFound extends RuntimeException {
    public OrderNotFound(OrderId orderId) {
        super(String.format("%s not found", orderId));
    }
}
