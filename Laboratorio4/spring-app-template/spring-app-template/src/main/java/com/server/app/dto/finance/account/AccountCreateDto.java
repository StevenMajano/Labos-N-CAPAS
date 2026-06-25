package com.server.app.dto.finance.account;

import com.server.app.enums.AccountType;
import com.server.app.enums.CurrencyType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountCreateDto {

    @NotBlank
    private String alias;

    @NotNull
    private CurrencyType currency;

    @NotNull
    @DecimalMin(value = "0.00", message = "El saldo inicial no puede ser negativo")
    private BigDecimal baseBalance;

    @NotNull
    private AccountType type;



}