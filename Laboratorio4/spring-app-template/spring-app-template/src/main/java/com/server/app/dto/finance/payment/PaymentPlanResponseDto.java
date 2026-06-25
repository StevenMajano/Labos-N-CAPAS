package com.server.app.dto.finance.paymentplan;

import com.server.app.enums.finance.PaymentPlanStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class PaymentPlanResponseDto {

    private Integer id;
    private Integer installmentNumber;
    private BigDecimal capitalAmount;
    private BigDecimal interestAmount;
    private LocalDate dueDate;
    private PaymentPlanStatus status;
}