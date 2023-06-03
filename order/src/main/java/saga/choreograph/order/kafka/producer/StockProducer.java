package saga.choreograph.order.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import saga.choreograph.order.api.dto.StockDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockProducer {

    private final KafkaTemplate kafkaTemplate;

    public void order(StockDto stockDto) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            String value = mapper.writeValueAsString(stockDto);
            log.info("value={}", value);
            kafkaTemplate.send("order-create", value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void stockRollback(StockDto stockDto) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            String value = mapper.writeValueAsString(stockDto);
            log.info("value={}", value);
            kafkaTemplate.send("stock-rollback", value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
