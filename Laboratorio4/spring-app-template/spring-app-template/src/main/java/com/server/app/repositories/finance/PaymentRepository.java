package com.server.app.repositories.finance;

import com.server.app.entities.finance.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findByPaymentPlanLoanUserId(Integer userId);
}