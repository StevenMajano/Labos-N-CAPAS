package com.server.app.dto.finance.account;

import com.server.app.enums.finance.AccountType;
import lombok.Data;

@Data
public class AccountUpdateDto {

    private String alias;

    private AccountType type;
}