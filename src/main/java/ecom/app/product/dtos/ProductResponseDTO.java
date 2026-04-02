package ecom.app.product.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDTO {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String category;
    private String imageURL;
    private Boolean available;
}
