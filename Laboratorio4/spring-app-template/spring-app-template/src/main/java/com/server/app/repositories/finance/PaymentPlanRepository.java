package com.server.app.repositories.finance;

import com.server.app.entities.finance.PaymentPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentPlanRepository extends JpaRepository<PaymentPlan, Integer> {

    List<PaymentPlan> findByLoanId(Integer loanId);

    List<PaymentPlan> findByLoanIdOrderByInstallmentNumberAsc(Integer loanId);
}