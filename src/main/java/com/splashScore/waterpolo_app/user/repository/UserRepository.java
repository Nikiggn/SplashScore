package com.splashScore.waterpolo_app.user.repository;

import com.splashScore.waterpolo_app.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // Optional когато не е сиг (може да го има може и да го няма
   Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
