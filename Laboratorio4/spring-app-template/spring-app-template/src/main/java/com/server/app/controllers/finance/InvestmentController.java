package com.server.app.controllers.finance;

import com.server.app.dto.finance.investment.InvestmentCreateDto;
import com.server.app.dto.finance.investment.InvestmentResponseDto;
import com.server.app.services.finance.InvestmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.server.app.utils.AuthUtil.getCurrentUserId;

@RestController
@RequestMapping("/api/finance/investments")
public class InvestmentController {

    private final InvestmentService investmentService;

    public InvestmentController(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    @PostMapping
    public ResponseEntity<InvestmentResponseDto> create(
            @Valid @RequestBody InvestmentCreateDto dto
    ) {
        Integer userId = getCurrentUserId();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(investmentService.create(userId, dto));
    }
}