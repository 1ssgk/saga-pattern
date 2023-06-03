package saga.choreograph.payment.service;

import saga.choreograph.payment.dto.StockDto;

public interface PaymentService {
    void payment(StockDto stockDto);

    void rollbackStockRollback(String value);
}
