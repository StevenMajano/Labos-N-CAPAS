package com.server.app.controllers.finance;

import com.server.app.dto.finance.payment.PaymentCreateDto;
import com.server.app.dto.finance.payment.PaymentResponseDto;
import com.server.app.services.finance.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.server.app.utils.AuthUtil.getCurrentUserId;

@RestController
@RequestMapping("/api/finance/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDto> create(
            @Valid @RequestBody PaymentCreateDto dto
    ) {
        Integer userId = getCurrentUserId();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.create(userId, dto));
    }
}