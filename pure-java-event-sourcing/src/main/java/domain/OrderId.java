package domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Builder
public class OrderId {
    final private String value;

    private OrderId(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static OrderId of(String value) {
        return OrderId.builder().value(value).build();
    }
}
