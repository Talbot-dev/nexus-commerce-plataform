package ecom.app.order.dtos;

import ecom.app.order.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private BigDecimal total;
    private OrderStatus status;
    private List<OrderItemRequestDTO> items;
    private LocalDateTime createdAt;
}
