package ecom.app.order.services;

import ecom.app.cart.entities.CartItem;
import ecom.app.order.dtos.OrderItemRequestDTO;
import ecom.app.order.entities.Order;
import ecom.app.order.entities.OrderItem;
import ecom.app.order.model.OrderStatus;
import ecom.app.user.entities.User;
import ecom.app.cart.services.CartItemService;
import ecom.app.order.dtos.OrderResponseDTO;
import ecom.app.order.repository.OrderRepository;
import ecom.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartItemService cartItemService;
    private final UserRepository userRepository;

    public Optional<OrderResponseDTO> createOrder(String userId) {
        // Validate for existing car items
        List<CartItem> cartItems = cartItemService.fetchProducts(Long.valueOf(userId));
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

        // Validate for valid and existing user
        Optional<User> existingUser = userRepository.findById(Long.valueOf(userId));
        if (existingUser.isEmpty()) {
            return Optional.empty();
        }
        User user = existingUser.get();

        // Calculate total price of the products
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPriceAccumulated)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotal(totalPrice);

        List<OrderItem> orderItems = cartItems.stream()
                        .map(cartItem -> new OrderItem(
                                null,
                                cartItem.getProduct(),
                                cartItem.getQuantity(),
                                cartItem.getPriceAccumulated(),
                                order
                        )).toList();
        order.setItems(orderItems);
        // Clear the cart
        cartItemService.clearCart(userId);
        return Optional.of(convertToResponseDTO(orderRepository.save(order)));
    }

    private OrderResponseDTO convertToResponseDTO(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getTotal(),
                order.getStatus(),
                order.getItems().stream()
                        .map(item -> new OrderItemRequestDTO(
                                item.getOrderId(),
                                item.getProduct().getId(),
                                item.getQuantity(),
                                item.getPrice(),
                                item.getPrice()
                        )).toList(),
                order.getCreatedAt()
        );
    }
}
