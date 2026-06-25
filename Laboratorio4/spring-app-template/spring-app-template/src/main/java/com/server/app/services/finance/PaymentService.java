package com.server.app.services.finance;

import com.server.app.dto.finance.payment.PaymentCreateDto;
import com.server.app.dto.finance.payment.PaymentResponseDto;

public interface PaymentService {

    PaymentResponseDto create(Integer userId, PaymentCreateDto dto);
}