package ecom.app.cart.controllers;

import ecom.app.cart.dtos.CartItemRequestDTO;

import ecom.app.cart.entities.CartItem;
import ecom.app.cart.services.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;

    @PostMapping("/api/cart")
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-user-ID") Long userId,
            @RequestBody CartItemRequestDTO dto) {
        return cartItemService.registerProductInCart(userId, dto) ?
                ResponseEntity.status(HttpStatus.CREATED).build() :
                ResponseEntity.badRequest().body("User or product not found or product out of stock");
    }

    @DeleteMapping("/api/cart/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-user-ID") String userId,
            @PathVariable Long productId
    ){
        return cartItemService.deleteProductFromCart(userId, productId ) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();

    }

    @GetMapping("/api/cart")
    public ResponseEntity<List<CartItem>> fetchCartItems(@RequestHeader("X-user-ID") Long userId) {
        return ResponseEntity.ok(cartItemService.fetchProducts(userId));
    }
}