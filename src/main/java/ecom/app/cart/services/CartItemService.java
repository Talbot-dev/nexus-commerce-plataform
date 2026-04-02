package ecom.app.cart.services;

import ecom.app.cart.dtos.CartItemRequestDTO;
import ecom.app.cart.dtos.CartItemResponseDTO;
import ecom.app.cart.entities.CartItem;
import ecom.app.cart.repository.CartItemRepository;
import ecom.app.product.entities.Product;
import ecom.app.product.repository.ProductRepository;
import ecom.app.user.entities.User;
import ecom.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

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

    /*private CartItemResponseDTO convertEntityToResponse(CartItem entity) {
        CartItemResponseDTO responseDTO = new CartItemResponseDTO();
        responseDTO.setId(String.valueOf(entity.getId()));
        responseDTO.setName(entity.getName());
        responseDTO.setDescription(entity.getDescription());
        responseDTO.setPrice(entity.getPrice());
        responseDTO.setStock(entity.getStock());
        responseDTO.setCategory(entity.getCategory());
        responseDTO.setImageURL(entity.getImageURL());
        responseDTO.setAvailable(entity.getAvailable());
        return responseDTO;
    }*/

    private CartItem convertRequestToEntity(CartItemRequestDTO dto, CartItem entity) {
        entity.setId(dto.getProductId());
        entity.setQuantity(BigDecimal.valueOf(dto.getQuantity()));
        return entity;
    }
}
