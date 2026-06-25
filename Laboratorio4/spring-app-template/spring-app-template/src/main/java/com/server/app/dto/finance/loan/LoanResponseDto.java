package com.server.app.dto.finance.loan;

import com.server.app.enums.finance.LoanStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class LoanResponseDto {

    private Integer id;
    private BigDecimal requestedCapital;
    private BigDecimal annualInterestRate;
    private Integer termMonths;
    private LoanStatus status;
}