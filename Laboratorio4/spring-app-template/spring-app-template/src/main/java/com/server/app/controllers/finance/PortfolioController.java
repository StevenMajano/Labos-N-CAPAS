package com.server.app.controllers.finance;

import com.server.app.dto.finance.portfolio.PortfolioCreateDto;
import com.server.app.dto.finance.portfolio.PortfolioPerformanceDto;
import com.server.app.dto.finance.portfolio.PortfolioResponseDto;
import com.server.app.services.finance.PortfolioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.server.app.utils.AuthUtil.getCurrentUserId;

@RestController
@RequestMapping("/api/finance/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping
    public ResponseEntity<PortfolioResponseDto> create(
            @Valid @RequestBody PortfolioCreateDto dto
    ) {
        Integer userId = getCurrentUserId();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(portfolioService.create(userId, dto));
    }

    @GetMapping
    public ResponseEntity<List<PortfolioResponseDto>> findAll() {
        Integer userId = getCurrentUserId();

        return ResponseEntity.ok(portfolioService.findAllByUser(userId));
    }

    @GetMapping("/{id}/performance")
    public ResponseEntity<PortfolioPerformanceDto> getPerformance(
            @PathVariable Integer id
    ) {
        Integer userId = getCurrentUserId();

        return ResponseEntity.ok(
                portfolioService.getPerformance(id, userId)
        );
    }
}