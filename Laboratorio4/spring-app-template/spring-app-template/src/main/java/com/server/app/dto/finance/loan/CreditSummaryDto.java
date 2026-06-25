package com.server.app.dto.finance.loan;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreditSummaryDto {

    private Integer totalLoans;
    private BigDecimal totalRequestedCapital;
    private BigDecimal totalPendingCapital;
}