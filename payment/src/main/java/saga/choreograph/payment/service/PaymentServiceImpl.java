package saga.choreograph.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import saga.choreograph.payment.dto.StockDto;
import saga.choreograph.payment.producer.StockProducer;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final StockProducer stockProducer;

    @Override
    public void payment(StockDto stockDto) {
        log.info("payment Success!!!");
    }

    @Override
    public void rollbackStockRollback(String value) {
        stockProducer.rollbackStockRollback(value);
    }
}
