package com.server.app.entities.finance;

import com.server.app.enums.CurrencyType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "original_currency", nullable = false, length = 20)
    private CurrencyType originalCurrency;

    @Column(name = "exchange_rate", nullable = false, precision = 15, scale = 6)
    private BigDecimal exchangeRate;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(length = 255)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}