package com.server.app.entities.finance;

import com.server.app.enums.finance.PaymentPlanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payment_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "installment_number", nullable = false)
    private Integer installmentNumber;

    @Column(name = "capital_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal capitalAmount;

    @Column(name = "interest_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal interestAmount;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentPlanStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;
}