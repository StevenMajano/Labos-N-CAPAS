package com.server.app.dto.finance.movement;

import com.server.app.enums.finance.CurrencyType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MovementCreateDto {

    @NotNull
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    private BigDecimal amount;


    @NotNull
    private CurrencyType originalCurrency;

    @NotNull
    @DecimalMin(value = "0.000001", message = "La tasa de cambio debe ser mayor a cero")
    private BigDecimal exchangeRate;

    private String description;

    @NotNull
    private Integer accountId;

    @NotNull
    private Integer categoryId;




}