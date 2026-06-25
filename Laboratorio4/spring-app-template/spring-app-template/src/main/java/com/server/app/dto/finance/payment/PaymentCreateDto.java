package com.server.app.dto.finance.payment;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentCreateDto {

    @NotNull
    private Integer paymentPlanId;

    @NotNull
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    private BigDecimal amount;
}