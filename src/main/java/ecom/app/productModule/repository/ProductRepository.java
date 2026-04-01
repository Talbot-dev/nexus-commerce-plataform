package ecom.app.productModule.repository;

import ecom.app.productModule.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByAvailableTrue();

    @Query("SELECT p FROM product_table p WHERE p.available = true AND p.stock > 0 AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> findByKeyword(@Param("keyword") String keyword);
}
