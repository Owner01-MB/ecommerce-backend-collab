package com.ecommerce.backend.repository;

import com.ecommerce.backend.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

  Optional<Object> findByEmail(String username);
    boolean existsByEmailIgnoreCaseAndIsActive(String email, boolean b);
    User findByEmailIgnoreCase(String email);
}
