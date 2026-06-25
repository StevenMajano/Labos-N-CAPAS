package com.server.app.services.finance.impl;

import com.server.app.dto.finance.movement.MovementCreateDto;
import com.server.app.dto.finance.movement.MovementResponseDto;
import com.server.app.entities.finance.Account;
import com.server.app.entities.finance.Category;
import com.server.app.entities.finance.Movement;
import com.server.app.enums.CategoryType;
import com.server.app.exceptions.BadRequestException;
import com.server.app.exceptions.NotFoundException;
import com.server.app.repositories.finance.AccountRepository;
import com.server.app.repositories.finance.CategoryRepository;
import com.server.app.repositories.finance.MovementRepository;
import com.server.app.services.finance.MovementService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    public MovementServiceImpl(
            MovementRepository movementRepository,
            AccountRepository accountRepository,
            CategoryRepository categoryRepository
    ) {
        this.movementRepository = movementRepository;
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public MovementResponseDto create(Integer userId, MovementCreateDto dto) {
        Account account = accountRepository.findByIdAndUserId(dto.getAccountId(), userId)
                .orElseThrow(() -> new NotFoundException("La cuenta no existe"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("La categoría no existe"));

        if (dto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("El monto debe ser mayor a cero");
        }

        if (category.getType() == CategoryType.INCOME) {
            account.setBaseBalance(account.getBaseBalance().add(dto.getAmount()));
        }

        if (category.getType() == CategoryType.EXPENSE) {
            if (account.getBaseBalance().compareTo(dto.getAmount()) < 0) {
                throw new BadRequestException("Saldo insuficiente");
            }

            account.setBaseBalance(account.getBaseBalance().subtract(dto.getAmount()));
        }

        Movement movement = Movement.builder()
                .amount(dto.getAmount())
                .originalCurrency(dto.getOriginalCurrency())
                .exchangeRate(dto.getExchangeRate())
                .date(LocalDateTime.now())
                .description(dto.getDescription())
                .account(account)
                .category(category)
                .build();

        accountRepository.save(account);

        return toResponse(movementRepository.save(movement));
    }

    @Override
    public List<MovementResponseDto> findAllByUser(Integer userId) {
        return movementRepository.findByAccountUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public MovementResponseDto findById(Integer movementId, Integer userId) {
        Movement movement = movementRepository.findByIdAndAccountUserId(movementId, userId)
                .orElseThrow(() -> new NotFoundException("El movimiento no existe"));

        return toResponse(movement);
    }

    private MovementResponseDto toResponse(Movement movement) {
        return MovementResponseDto.builder()
                .id(movement.getId())
                .amount(movement.getAmount())
                .originalCurrency(movement.getOriginalCurrency())
                .exchangeRate(movement.getExchangeRate())
                .date(movement.getDate())
                .description(movement.getDescription())
                .accountId(movement.getAccount().getId())
                .categoryId(movement.getCategory().getId())
                .build();
    }
}