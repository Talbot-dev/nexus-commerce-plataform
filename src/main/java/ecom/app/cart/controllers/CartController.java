package ecom.app.cart.controllers;

import ecom.app.cart.dtos.CartItemRequestDTO;

import ecom.app.cart.services.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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
}