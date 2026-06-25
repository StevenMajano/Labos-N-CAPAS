package com.server.app.entities.finance;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "assets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 20)
    private String symbol;

    @Column(nullable = false, length = 100)
    private String market;

    @Column(name = "market_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal marketPrice;

    @Column(nullable = false, length = 100)
    private String sector;
}