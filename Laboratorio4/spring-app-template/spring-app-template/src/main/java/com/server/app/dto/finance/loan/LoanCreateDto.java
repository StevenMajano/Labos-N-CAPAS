package com.server.app.dto.finance.loan;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanCreateDto {

    @NotNull
    @DecimalMin(value = "0.01", message = "El capital solicitado debe ser mayor a cero")
    private BigDecimal requestedCapital;

    @NotNull
    @DecimalMin(value = "0.01", message = "La tasa de interés debe ser mayor a cero")
    private BigDecimal annualInterestRate;

    @NotNull
    private Integer termMonths;
}