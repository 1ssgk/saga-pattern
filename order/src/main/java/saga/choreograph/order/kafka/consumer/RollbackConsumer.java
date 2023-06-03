package saga.choreograph.order.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import saga.choreograph.order.api.dto.StockDto;
import saga.choreograph.order.api.service.OrderService;

@Slf4j
@Component
@RequiredArgsConstructor
public class RollbackConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "order-rollback", groupId = "group-01")
    public void rollbackOrder(String orderId) {
        log.error("[Rollback] order-rollback, orderId :{}", orderId);
        orderService.cancel(Long.parseLong(orderId));
    }

}
