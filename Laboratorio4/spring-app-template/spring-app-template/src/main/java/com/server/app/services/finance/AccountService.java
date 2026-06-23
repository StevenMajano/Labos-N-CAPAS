package com.server.app.services.finance;

import com.server.app.dto.finance.account.AccountCreateDto;
import com.server.app.dto.finance.account.AccountResponseDto;
import com.server.app.dto.finance.account.AccountUpdateDto;

import java.util.List;

public interface AccountService {

    AccountResponseDto create(
            Integer userId,
            AccountCreateDto dto
    );

    List<AccountResponseDto> findAllByUser(
            Integer userId
    );

    AccountResponseDto findById(
            Integer accountId,
            Integer userId
    );

    AccountResponseDto update(
            Integer accountId,
            Integer userId,
            AccountUpdateDto dto
    );

    void delete(
            Integer accountId,
            Integer userId
    );
}