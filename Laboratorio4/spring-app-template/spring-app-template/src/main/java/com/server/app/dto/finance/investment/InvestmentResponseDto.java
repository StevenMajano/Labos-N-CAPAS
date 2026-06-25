package com.server.app.dto.finance.investment;

import com.server.app.enums.finance.InvestmentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class InvestmentResponseDto {

    private Integer id;
    private BigDecimal quantity;
    private BigDecimal purchasePrice;
    private LocalDateTime date;
    private InvestmentStatus status;
    private Integer portfolioId;
    private Integer assetId;
}