package com.server.app.services.finance;

import com.server.app.dto.finance.portfolio.PortfolioCreateDto;
import com.server.app.dto.finance.portfolio.PortfolioPerformanceDto;
import com.server.app.dto.finance.portfolio.PortfolioResponseDto;

import java.util.List;

public interface PortfolioService {

    PortfolioResponseDto create(
            Integer userId,
            PortfolioCreateDto dto
    );

    List<PortfolioResponseDto> findAllByUser(
            Integer userId
    );

    PortfolioPerformanceDto getPerformance(
            Integer portfolioId,
            Integer userId
    );
}