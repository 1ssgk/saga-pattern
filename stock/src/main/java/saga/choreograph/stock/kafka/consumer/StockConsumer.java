package saga.choreograph.stock.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import saga.choreograph.stock.kafka.dto.StockDto;
import saga.choreograph.stock.kafka.service.StockServiceImpl;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockConsumer {

    private final StockServiceImpl stockService;

    @KafkaListener(topics = "order-create", groupId = "group-01")
    public void decrease(String value) {
        StockDto dto = makeStockDto(value);
        try {
            log.info("[Stock SERVER] order-create", dto);
            // 재고 감소
            stockService.decrease(dto);
        } catch (Exception e) {
            log.error("==== [Rollback] stock-rollback, orderId : {}", dto.getOrderId());
            // 주문 취소 전달
            stockService.rollbackCreatedOrder(dto.getOrderId());
        }
    }

    @KafkaListener(topics = "stock-rollback")
    public void stockRollback(String value) {
        StockDto dto = makeStockDto(value);
        try {
            log.info("[Stock SERVER] stock-rollback", dto);
            // 재고 복원
            stockService.rollbackStock(dto);
            // 주문 취소 전달
            stockService.rollbackCreatedOrder(dto.getOrderId());
        } catch (Exception e) {
            log.error("==== [Rollback FAIL] stock-rollback, data ={}",dto);
        }
    }

    private StockDto makeStockDto(String value) {
        StockDto dto = new StockDto();
        try {
            ObjectMapper mapper = new ObjectMapper();
            dto = mapper.readValue(value, StockDto.class);
        } catch (JsonProcessingException e) {
            log.error("[JsonProcessingException] ={}", e.getMessage());
        }
        return dto;
    }
}
