package com.server.app.dto.finance.transfer;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequestDto {

    @NotNull
    private Integer originAccountId;

    @NotNull
    private Integer destinationAccountId;

    @NotNull
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    private BigDecimal amount;

    private String description;


}