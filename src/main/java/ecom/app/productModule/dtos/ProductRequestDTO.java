package ecom.app.productModule.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String imageURL;
}
