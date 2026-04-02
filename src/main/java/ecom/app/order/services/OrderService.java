package ecom.app.order.services;

import ecom.app.order.dtos.OrderResponseDTO;
import ecom.app.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderResponseDTO createOrder(String userId) {
    }
}
