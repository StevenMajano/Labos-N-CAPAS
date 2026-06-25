package com.server.app.entities.finance;

import com.server.app.entities.User;
import com.server.app.enums.RiskProfile;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "portfolios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "total_balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_profile", nullable = false, length = 20)
    private RiskProfile riskProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}