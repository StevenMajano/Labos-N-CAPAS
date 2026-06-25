package com.server.app.entities.finance;

import com.server.app.enums.InvestmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "investments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, precision = 15, scale = 4)
    private BigDecimal quantity;

    @Column(name = "purchase_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal purchasePrice;

    @Column(nullable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InvestmentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;
}