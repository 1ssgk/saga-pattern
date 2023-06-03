package saga.choreograph.order.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saga.choreograph.order.api.entity.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private String userId;
    private List<OrderItemDto> orderItems;

    public static Order toEntity(OrderDto orderDto) {
        return Order.builder()
                .userId(orderDto.userId)
                .orderItems(
                        orderDto.orderItems.stream()
                                .map(OrderItemDto::toEntity)
                                .collect(Collectors.toList()))
                .orderDate(LocalDateTime.now())
                .build();
    }


}
