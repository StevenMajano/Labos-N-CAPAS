package com.server.app.services.finance;

import com.server.app.dto.finance.investment.InvestmentCreateDto;
import com.server.app.dto.finance.investment.InvestmentResponseDto;

public interface InvestmentService {

    InvestmentResponseDto create(
            Integer userId,
            InvestmentCreateDto dto
    );
}