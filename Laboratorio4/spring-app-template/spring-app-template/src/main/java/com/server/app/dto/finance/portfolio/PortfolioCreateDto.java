package com.server.app.dto.finance.portfolio;

import com.server.app.enums.finance.RiskProfile;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PortfolioCreateDto {

    @NotBlank
    private String name;

    @NotNull
    @DecimalMin(value = "0.00", message = "El saldo total no puede ser negativo")
    private BigDecimal totalBalance;

    @NotNull
    private RiskProfile riskProfile;
}