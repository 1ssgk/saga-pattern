package saga.choreograph.stock.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import saga.choreograph.stock.kafka.dto.StockDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentProducer {
    private final KafkaTemplate kafkaTemplate;

    public void payment(StockDto stockDto) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            String value = mapper.writeValueAsString(stockDto);
            log.info("value={}", value);
            kafkaTemplate.send("stock-decrease", value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
