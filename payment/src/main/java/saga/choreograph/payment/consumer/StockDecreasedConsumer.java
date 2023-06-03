package saga.choreograph.payment.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import saga.choreograph.payment.dto.StockDto;
import saga.choreograph.payment.service.PaymentService;

import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockDecreasedConsumer {

    private final PaymentService paymentService;
    private static final int BETWEEN_ZERO_AND_ONE = 2;

    @KafkaListener(topics = "stock-decrease", groupId = "group-01")
    public void payment(String value) {
        StockDto dto = new StockDto();

        try {
            ObjectMapper mapper = new ObjectMapper();
            dto = mapper.readValue(value, StockDto.class);
            log.info("[Payment SERVER] stock-decrease", dto);
            paymentService.payment(dto);
            errorPerHalf();
        } catch (JsonProcessingException e) {
            log.error("[JsonProcessingException] ={}", e.getMessage());
        } catch (Exception e) {
            log.error("==== [Rollback] stock-rollback, orderId : {}", dto.getOrderId());
            paymentService.rollbackStockRollback(value);
        }
    }


    private void errorPerHalf() {
        int zeroOrOne = new Random().nextInt(BETWEEN_ZERO_AND_ONE);

        if (zeroOrOne == 0) {
            throw new RuntimeException("Error payment module");
        }
    }
}
