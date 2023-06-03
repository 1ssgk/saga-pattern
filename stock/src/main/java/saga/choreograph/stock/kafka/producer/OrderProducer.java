package saga.choreograph.stock.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate kafkaTemplate;

    public void rollbackCreatedOrder(String orderId) {
        kafkaTemplate.send("order-rollback", orderId);
    }
}
