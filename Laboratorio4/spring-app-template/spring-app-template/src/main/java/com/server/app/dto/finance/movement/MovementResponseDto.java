package com.server.app.dto.finance.movement;

import com.server.app.enums.CurrencyType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class MovementResponseDto {

    private Integer id;

    private BigDecimal amount;

    private CurrencyType originalCurrency;

    private BigDecimal exchangeRate;

    private LocalDateTime date;

    private String description;

    private Integer accountId;

    private Integer categoryId;
}