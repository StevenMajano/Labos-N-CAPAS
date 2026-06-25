package com.server.app.repositories.finance;

import com.server.app.entities.finance.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Integer> {

    List<Investment> findByPortfolioId(Integer portfolioId);

    List<Investment> findByPortfolioUserId(Integer userId);
}
