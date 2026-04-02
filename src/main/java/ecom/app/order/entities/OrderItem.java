package ecom.app.order.entities;

import ecom.app.product.entities.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    private BigDecimal quantity;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Order order;
}