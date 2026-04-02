package ecom.app.order.controllers;

import ecom.app.order.dtos.OrderResponseDTO;
import ecom.app.order.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class  OrderController {

    private final OrderService orderService;

    @PostMapping("/api/orders")
    public ResponseEntity<OrderResponseDTO> createOrder(
            @RequestHeader("X-User-ID")String userId) {
            return orderService.createOrder(userId)
                    .map(orderResponseDTO -> new ResponseEntity<>(orderResponseDTO, HttpStatus.CREATED))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
