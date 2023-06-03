package saga.choreograph.stock.kafka.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import saga.choreograph.stock.kafka.dto.ItemDto;
import saga.choreograph.stock.kafka.dto.StockDto;
import saga.choreograph.stock.kafka.entity.Item;
import saga.choreograph.stock.kafka.producer.OrderProducer;
import saga.choreograph.stock.kafka.producer.PaymentProducer;
import saga.choreograph.stock.kafka.repository.ItemRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final ItemRepository itemRepository;

    private final OrderProducer orderProducer;

    private final PaymentProducer paymentProducer;

    @Override
    public void decrease(StockDto stockDto) {
        int price = 0;

        for (ItemDto orderItem : stockDto.getOrderItems()) {
            Item item = getItem(orderItem);

            int orderItemPrice = item.getPrice() * orderItem.getStock();
            item.decreaseStock(orderItem.getStock());
            itemRepository.save(item);

            price += orderItemPrice;
        }

        paymentProducer.payment(stockDto);
    }



    @Override
    public void rollbackCreatedOrder(Long orderId) {
        orderProducer.rollbackCreatedOrder(orderId.toString());
    }

    public void rollbackStock(StockDto stockDto) {
        for (ItemDto orderItem : stockDto.getOrderItems()) {
            Item item = getItem(orderItem);

            item.rollbackStock(orderItem.getStock());
            itemRepository.save(item);
        }
    }

    private Item getItem(ItemDto orderItem) {
        return itemRepository.findById(orderItem.getItemId())
                .orElseThrow(() -> {
                    throw new IllegalStateException("존재하지 않는 데이터 잆니다.");
                });
    }
}
