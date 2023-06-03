package saga.choreograph.order.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import saga.choreograph.order.api.dto.OrderDto;
import saga.choreograph.order.api.service.OrderService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public void order(@RequestBody OrderDto orderDto) {
        orderService.order(orderDto);
    }

}
