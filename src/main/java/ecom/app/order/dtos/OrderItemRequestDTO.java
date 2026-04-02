package ecom.app.order.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemRequestDTO {

    private Long id;
    private Long productId;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
}
