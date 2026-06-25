package com.server.app.dto.finance.payment;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class PaymentResponseDto {

    private Integer id;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private BigDecimal lateFee;
    private Integer paymentPlanId;
}