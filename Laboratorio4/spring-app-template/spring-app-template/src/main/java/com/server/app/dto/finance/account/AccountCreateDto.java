package com.server.app.dto.finance.account;

import com.server.app.enums.finance.AccountType;
import com.server.app.enums.finance.CurrencyType;
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
    private BigDecimal baseBalance;

    @NotNull
    private AccountType type;
}