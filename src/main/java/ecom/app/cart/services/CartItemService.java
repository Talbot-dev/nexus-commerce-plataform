package ecom.app.cart.services;

import ecom.app.cart.dtos.CartItemRequestDTO;
import ecom.app.cart.entities.CartItem;
import ecom.app.cart.repository.CartItemRepository;
import ecom.app.product.entities.Product;
import ecom.app.user.entities.User;
import ecom.app.product.repository.ProductRepository;
import ecom.app.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<CartItem> fetchProducts(Long  userId) {
        return userRepository.findById(userId)
                .map(cartItemRepository::findByUser)
                .orElseGet(List::of);
    }

    public boolean registerProductInCart(Long userId, CartItemRequestDTO request) {
        Optional<Product> existingProduct = productRepository.findById(request.getProductId());
        if (existingProduct.isEmpty())
            return false;
        Product product = existingProduct.get();
        if (product.getStock() < request.getQuantity())
            return false;
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty())
            return false;
        User user = existingUser.get();
        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);

        if (existingCartItem != null) {
            // Update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity().add(BigDecimal.valueOf(request.getQuantity())));
            existingCartItem.setQuantity(product.getPrice().multiply((existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else {
            // Create the stock
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(BigDecimal.valueOf(request.getQuantity()));
            cartItem.setPriceAccumulated(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteProductFromCart(String userId, Long productId) {
        Optional<Product> existingProduct = productRepository.findById(productId);
        Optional<User> existingUser = userRepository.findById(Long.valueOf(userId));

        if (existingUser.isPresent() && existingProduct.isPresent()) {
            cartItemRepository.deleteByUserAndProduct(existingUser.get(), existingProduct.get());
            return true;
        }
        return false;
    }

    public void clearCart(String userId) {
        userRepository.findById(Long.valueOf(userId)).ifPresent(cartItemRepository::deleteByUser);
    }
}