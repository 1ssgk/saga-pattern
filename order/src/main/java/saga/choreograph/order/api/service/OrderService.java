package saga.choreograph.order.api.service;

import saga.choreograph.order.api.dto.OrderDto;
import saga.choreograph.order.api.dto.StockDto;

public interface OrderService {
    // 주문
    void order(OrderDto orderDto);
    // 취소
    void cancel(Long orderId);
}
