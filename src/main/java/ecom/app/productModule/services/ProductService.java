package ecom.app.productModule.services;

import ecom.app.productModule.dtos.ProductRequestDTO;
import ecom.app.productModule.dtos.ProductResponseDTO;
import ecom.app.productModule.entities.Product;
import ecom.app.productModule.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product productCreated = new Product();
        return convertEntityToResponse(productRepository.save(convertRequestToEntity(productRequestDTO, productCreated)));
    }


// Not working
    public ProductResponseDTO updateExistingProduct(Long id,  ProductRequestDTO productRequestDTO) {
        productRepository.findById(id).stream()
                .peek(existingProduct ->{
                    existingProduct.setName(productRequestDTO.getName());
                    existingProduct.setDescription(productRequestDTO.getDescription());
                    existingProduct.setPrice(productRequestDTO.getPrice());
                    existingProduct.setCategory(productRequestDTO.getCategory());
                });
        return null;
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
