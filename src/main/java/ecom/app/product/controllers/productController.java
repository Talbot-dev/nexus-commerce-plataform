package ecom.app.product.controllers;

import ecom.app.product.dtos.ProductRequestDTO;
import ecom.app.product.dtos.ProductResponseDTO;
import ecom.app.product.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/")
public class productController {

    private final ProductService productService;

    public productController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        return new ResponseEntity<>(productService.createProduct(productRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id,  @RequestBody ProductRequestDTO productRequestDTO) {
        return productService.updateExistingProduct(id, productRequestDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> fetchAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateAvailableProduct(@PathVariable Long id){
        return productService.deactivateProductById(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<ProductResponseDTO>> fetchProductByName(@PathVariable String name){
        return ResponseEntity.ok(productService.getSingleProduct(name));
    }
}
