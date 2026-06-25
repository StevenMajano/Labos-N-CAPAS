package com.server.app.controllers.finance;

import com.server.app.dto.finance.loan.CreditSummaryDto;
import com.server.app.dto.finance.loan.LoanCreateDto;
import com.server.app.dto.finance.loan.LoanResponseDto;
import com.server.app.dto.finance.paymentplan.PaymentPlanResponseDto;
import com.server.app.services.finance.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.server.app.utils.AuthUtil.getCurrentUserId;

@RestController
@RequestMapping("/api/finance")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/loans")
    public ResponseEntity<LoanResponseDto> create(
            @Valid @RequestBody LoanCreateDto dto
    ) {
        Integer userId = getCurrentUserId();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(loanService.create(userId, dto));
    }

    @GetMapping("/loans")
    public ResponseEntity<List<LoanResponseDto>> findAll() {
        Integer userId = getCurrentUserId();

        return ResponseEntity.ok(
                loanService.findAllByUser(userId)
        );
    }

    @GetMapping("/loans/{id}/payment-plans")
    public ResponseEntity<List<PaymentPlanResponseDto>> findPaymentPlans(
            @PathVariable Integer id
    ) {
        Integer userId = getCurrentUserId();

        return ResponseEntity.ok(
                loanService.findPaymentPlans(id, userId)
        );
    }

    @GetMapping("/credit-summary")
    public ResponseEntity<CreditSummaryDto> getCreditSummary() {
        Integer userId = getCurrentUserId();

        return ResponseEntity.ok(
                loanService.getCreditSummary(userId)
        );
    }
}