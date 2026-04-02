package ecom.app.product.services;

import ecom.app.product.dtos.ProductRequestDTO;
import ecom.app.product.dtos.ProductResponseDTO;
import ecom.app.product.entities.Product;
import ecom.app.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository accessToDatabase;

    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product productCreated = new Product();
        return convertEntityToResponse(accessToDatabase.save(convertRequestToEntity(productRequestDTO, productCreated)));
    }

    public Optional<ProductResponseDTO> updateExistingProduct(Long id, ProductRequestDTO productRequestDTO) {
        return accessToDatabase.findById(id)
                .map(existingProduct ->{
                    convertRequestToEntity(productRequestDTO, existingProduct);
                    return convertEntityToResponse(accessToDatabase.save(existingProduct));
                });
    }

    public List<ProductResponseDTO> getAllProducts() {
        return accessToDatabase.findByAvailableTrue().stream()
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
    }

    public Boolean deactivateProductById(Long id) {
        return accessToDatabase.findById(id)
                .map(product -> {
                    product.setAvailable(false);
                    accessToDatabase.save(product);
                    return true;
                }).orElse(false);
    }

    public List<ProductResponseDTO> getSingleProduct(String keyword) {
        return accessToDatabase.findByKeyword(keyword).stream()
                .map(this::convertEntityToResponse)
                .collect(Collectors.toList());
    }

    private ProductResponseDTO convertEntityToResponse(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(String.valueOf(product.getId()));
        productResponseDTO.setName(product.getName());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setStock(product.getStock());
        productResponseDTO.setCategory(product.getCategory());
        productResponseDTO.setImageURL(product.getImageURL());
        productResponseDTO.setAvailable(product.getAvailable());
        return productResponseDTO;
    }

    private Product convertRequestToEntity(ProductRequestDTO productRequestDTO, Product product) {
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setCategory(productRequestDTO.getCategory());
        product.setImageURL(productRequestDTO.getImageURL());
        //Me falta la variable de disponibilidad
        return product;
    }
}
