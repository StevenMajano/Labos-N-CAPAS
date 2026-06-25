package com.server.app.services.finance;

import com.server.app.dto.finance.loan.CreditSummaryDto;
import com.server.app.dto.finance.loan.LoanCreateDto;
import com.server.app.dto.finance.loan.LoanResponseDto;
import com.server.app.dto.finance.paymentplan.PaymentPlanResponseDto;

import java.util.List;

public interface LoanService {

    LoanResponseDto create(Integer userId, LoanCreateDto dto);

    List<LoanResponseDto> findAllByUser(Integer userId);

    List<PaymentPlanResponseDto> findPaymentPlans(Integer loanId, Integer userId);

    CreditSummaryDto getCreditSummary(Integer userId);
}