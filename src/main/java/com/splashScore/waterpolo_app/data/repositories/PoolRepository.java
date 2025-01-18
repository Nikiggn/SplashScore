package com.splashScore.waterpolo_app.data.repositories;

import com.splashScore.waterpolo_app.data.entities.Pool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoolRepository extends JpaRepository<Pool, Integer> {
}
