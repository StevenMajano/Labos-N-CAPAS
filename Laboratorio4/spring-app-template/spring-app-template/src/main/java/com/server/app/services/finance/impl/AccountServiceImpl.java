package com.server.app.services.finance.impl;

import com.server.app.dto.finance.account.AccountCreateDto;
import com.server.app.dto.finance.account.AccountResponseDto;
import com.server.app.dto.finance.account.AccountUpdateDto;
import com.server.app.entities.User;
import com.server.app.entities.finance.Account;
import com.server.app.exceptions.BadRequestException;
import com.server.app.exceptions.NotFoundException;
import com.server.app.repositories.UserRepository;
import com.server.app.repositories.finance.AccountRepository;
import com.server.app.services.finance.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountServiceImpl(
            AccountRepository accountRepository,
            UserRepository userRepository
    ) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AccountResponseDto create(Integer userId, AccountCreateDto dto) {
        User user = findUserById(userId);

        if (accountRepository.existsByAliasAndUserId(dto.getAlias(), userId)) {
            throw new BadRequestException("Ya existe una cuenta con ese alias");
        }

        Account account = Account.builder()
                .alias(dto.getAlias())
                .currency(dto.getCurrency())
                .baseBalance(dto.getBaseBalance())
                .type(dto.getType())
                .user(user)
                .build();

        return toResponse(accountRepository.save(account));
    }

    @Override
    public List<AccountResponseDto> findAllByUser(Integer userId) {
        return accountRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public AccountResponseDto findById(Integer accountId, Integer userId) {
        return toResponse(findAccountByIdAndUser(accountId, userId));
    }

    @Override
    public AccountResponseDto update(Integer accountId, Integer userId, AccountUpdateDto dto) {
        Account account = findAccountByIdAndUser(accountId, userId);

        if (dto.getAlias() != null && !dto.getAlias().isBlank()) {
            account.setAlias(dto.getAlias());
        }

        if (dto.getType() != null) {
            account.setType(dto.getType());
        }

        return toResponse(accountRepository.save(account));
    }

    @Override
    public void delete(Integer accountId, Integer userId) {
        Account account = findAccountByIdAndUser(accountId, userId);
        accountRepository.delete(account);
    }

    private User findUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("El usuario no existe"));
    }

    private Account findAccountByIdAndUser(Integer accountId, Integer userId) {
        return accountRepository.findByIdAndUserId(accountId, userId)
                .orElseThrow(() -> new NotFoundException("La cuenta no existe"));
    }

    private AccountResponseDto toResponse(Account account) {
        return AccountResponseDto.builder()
                .id(account.getId())
                .alias(account.getAlias())
                .currency(account.getCurrency())
                .baseBalance(account.getBaseBalance())
                .type(account.getType())
                .build();
    }
}