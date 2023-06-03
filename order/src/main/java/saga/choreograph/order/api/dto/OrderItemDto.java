package saga.choreograph.order.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import saga.choreograph.order.api.entity.OrderItem;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private Long itemId;
    private int stock;

    public static OrderItem toEntity(OrderItemDto orderItemDto) {
        return OrderItem.builder()
                .itemId(orderItemDto.itemId)
                .stock(orderItemDto.stock)
                .build();
    }

    public static OrderItemDto of(OrderItem orderItem) {
        return OrderItemDto.builder()
                .itemId(orderItem.getItemId())
                .stock(orderItem.getStock())
                .build();
    }
}
