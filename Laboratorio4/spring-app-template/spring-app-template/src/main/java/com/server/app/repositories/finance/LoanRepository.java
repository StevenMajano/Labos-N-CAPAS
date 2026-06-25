package com.server.app.repositories.finance;

import com.server.app.entities.finance.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findByUserId(Integer userId);

    Optional<Loan> findByIdAndUserId(Integer id, Integer userId);
}
