package com.server.app.dto.finance.portfolio;

import com.server.app.enums.RiskProfile;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PortfolioResponseDto {

    private Integer id;
    private String name;
    private BigDecimal totalBalance;
    private RiskProfile riskProfile;
}