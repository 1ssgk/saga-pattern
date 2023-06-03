package saga.choreograph.stock.kafka.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    private int stock;

    public void decreaseStock(int orderItemStock) {
        int resultStock = this.stock - orderItemStock;

        if (this.stock == 0) {
            throw new IllegalStateException("[" + this.name + "] 해당 제품은 매진 되었습니다.");
        }
        if (resultStock < 0) {
            throw new IllegalStateException("[" + this.name + "] 주문 수량이 남아있는 수량보다 큽니다.");
        }

        this.stock = resultStock;
    }

    public void rollbackStock(int orderItemStock) {
        this.stock += orderItemStock;
    }

}
