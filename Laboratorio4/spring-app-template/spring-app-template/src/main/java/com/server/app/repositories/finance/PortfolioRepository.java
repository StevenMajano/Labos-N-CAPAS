package com.server.app.repositories.finance;

import com.server.app.entities.finance.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {

    List<Portfolio> findByUserId(Integer userId);

    Optional<Portfolio> findByIdAndUserId(Integer id, Integer userId);
}