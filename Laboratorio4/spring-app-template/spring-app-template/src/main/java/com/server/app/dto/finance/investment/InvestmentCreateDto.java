package com.server.app.dto.finance.investment;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvestmentCreateDto {

    @NotNull
    private Integer portfolioId;

    @NotNull
    private Integer assetId;

    @NotNull
    @DecimalMin(value = "0.0001", message = "La cantidad debe ser mayor a cero")
    private BigDecimal quantity;

    @NotNull
    @DecimalMin(value = "0.01", message = "El precio de compra debe ser mayor a cero")
    private BigDecimal purchasePrice;
}