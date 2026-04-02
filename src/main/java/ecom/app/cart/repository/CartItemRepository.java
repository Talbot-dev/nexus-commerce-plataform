package ecom.app.cart.repository;

import ecom.app.cart.entities.CartItem;
import ecom.app.product.entities.Product;
import ecom.app.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User user, Product product);
}
