package com.splashScore.waterpolo_app.country.repository;

import com.splashScore.waterpolo_app.country.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
