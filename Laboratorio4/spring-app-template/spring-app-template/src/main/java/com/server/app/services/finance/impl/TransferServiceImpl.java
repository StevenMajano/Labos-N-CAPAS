package com.server.app.services.finance.impl;

import com.server.app.dto.finance.transfer.TransferRequestDto;
import com.server.app.dto.finance.transfer.TransferResponseDto;
import com.server.app.entities.finance.Account;
import com.server.app.exceptions.BadRequestException;
import com.server.app.exceptions.NotFoundException;
import com.server.app.repositories.finance.AccountRepository;
import com.server.app.services.finance.TransferService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;

    public TransferServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public TransferResponseDto transferFunds(Integer userId, TransferRequestDto dto) {
        if (dto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("El monto debe ser mayor a cero");
        }

        if (dto.getOriginAccountId().equals(dto.getDestinationAccountId())) {
            throw new BadRequestException("La cuenta origen y destino no pueden ser la misma");
        }

        Account originAccount = accountRepository
                .findByIdAndUserId(dto.getOriginAccountId(), userId)
                .orElseThrow(() -> new NotFoundException("La cuenta origen no existe"));

        Account destinationAccount = accountRepository
                .findByIdAndUserId(dto.getDestinationAccountId(), userId)
                .orElseThrow(() -> new NotFoundException("La cuenta destino no existe"));

        if (originAccount.getBaseBalance().compareTo(dto.getAmount()) < 0) {
            throw new BadRequestException("Saldo insuficiente");
        }

        originAccount.setBaseBalance(
                originAccount.getBaseBalance().subtract(dto.getAmount())
        );

        destinationAccount.setBaseBalance(
                destinationAccount.getBaseBalance().add(dto.getAmount())
        );

        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);

        return TransferResponseDto.builder()
                .message("Transferencia realizada correctamente")
                .originAccountId(originAccount.getId())
                .destinationAccountId(destinationAccount.getId())
                .build();
    }
}