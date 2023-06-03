package saga.choreograph.order.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saga.choreograph.order.api.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
