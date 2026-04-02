package ecom.app.cart.dtos;

import lombok.Data;

@Data
public class    CartItemRequestDTO {
    private Long productId;
    private Integer quantity;
}
