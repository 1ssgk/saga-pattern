package saga.choreograph.stock.kafka.service;

import saga.choreograph.stock.kafka.dto.StockDto;

public interface StockService {
    void decrease(StockDto stockDto);

    void rollbackCreatedOrder(Long orderId);
}
