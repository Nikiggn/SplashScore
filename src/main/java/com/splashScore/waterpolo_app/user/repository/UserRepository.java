package com.splashScore.waterpolo_app.user.repository;

import com.splashScore.waterpolo_app.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
