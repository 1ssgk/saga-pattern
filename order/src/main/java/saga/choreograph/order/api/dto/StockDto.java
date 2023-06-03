package saga.choreograph.order.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saga.choreograph.order.api.entity.Order;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDto {
    private Long orderId;

    private List orderItems;

    public static StockDto of(Order order) {
        return StockDto.builder()
                .orderId(order.getId())
                .orderItems(order.getOrderItems().stream()
                        .map(OrderItemDto::of)
                        .collect(Collectors.toList()))
                .build();
    }
}
