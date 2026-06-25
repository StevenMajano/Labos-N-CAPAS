package com.server.app.dto.finance.account;

import com.server.app.enums.AccountType;
import com.server.app.enums.CurrencyType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountResponseDto {

    private Integer id;

    private String alias;

    private CurrencyType currency;

    private BigDecimal baseBalance;

    private AccountType type;
}