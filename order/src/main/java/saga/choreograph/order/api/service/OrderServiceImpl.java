package saga.choreograph.order.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import saga.choreograph.order.api.dto.OrderDto;
import saga.choreograph.order.api.dto.StockDto;
import saga.choreograph.order.api.entity.Order;
import saga.choreograph.order.api.entity.OrderItem;
import saga.choreograph.order.api.repository.OrderRepository;
import saga.choreograph.order.kafka.producer.StockProducer;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final StockProducer stockProducer;

    @Override
    public void order(OrderDto orderDto) {
        log.info("[orderService] order");

        List<OrderItem> orderItems = orderDto.getOrderItems().stream()
                .map(OrderItem::createOrderItem).toList();

        Order order = Order.createOrder(orderItems,orderDto.getUserId());

        orderRepository.save(order);
        stockProducer.order(StockDto.of(order));
    }

    @Override
    public void cancel(Long orderId) {
        log.info("[orderService] cancel");
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->{
                    throw new IllegalStateException("존재하지 않는 주문입니다.");
                });
        orderRepository.deleteById(orderId);
    }
}
