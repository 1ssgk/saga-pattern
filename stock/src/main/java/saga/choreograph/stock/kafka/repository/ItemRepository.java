package saga.choreograph.stock.kafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saga.choreograph.stock.kafka.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
