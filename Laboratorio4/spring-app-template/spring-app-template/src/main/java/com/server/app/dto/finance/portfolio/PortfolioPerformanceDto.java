package com.server.app.dto.finance.portfolio;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PortfolioPerformanceDto {

    private Integer portfolioId;
    private BigDecimal investedAmount;
    private BigDecimal currentValue;
    private BigDecimal profit;
}
