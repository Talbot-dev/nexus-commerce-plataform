package ecom.app.userModule.repository;

import ecom.app.userModule.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data access layer responsible for connecting and perform transactional operations into the database
 *
 * */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
