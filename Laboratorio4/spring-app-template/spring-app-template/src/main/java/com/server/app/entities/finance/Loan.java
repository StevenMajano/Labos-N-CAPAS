package com.server.app.entities.finance;

import com.server.app.entities.User;
import com.server.app.enums.finance.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "requested_capital", nullable = false, precision = 15, scale = 2)
    private BigDecimal requestedCapital;

    @Column(name = "annual_interest_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal annualInterestRate;

    @Column(name = "term_months", nullable = false)
    private Integer termMonths;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private LoanStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}