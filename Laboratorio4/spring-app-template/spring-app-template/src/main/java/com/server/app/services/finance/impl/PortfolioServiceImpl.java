package com.server.app.services.finance.impl;

import com.server.app.dto.finance.portfolio.PortfolioCreateDto;
import com.server.app.dto.finance.portfolio.PortfolioPerformanceDto;
import com.server.app.dto.finance.portfolio.PortfolioResponseDto;
import com.server.app.entities.User;
import com.server.app.entities.finance.Investment;
import com.server.app.entities.finance.Portfolio;
import com.server.app.exceptions.NotFoundException;
import com.server.app.repositories.UserRepository;
import com.server.app.repositories.finance.InvestmentRepository;
import com.server.app.repositories.finance.PortfolioRepository;
import com.server.app.services.finance.PortfolioService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final InvestmentRepository investmentRepository;
    private final UserRepository userRepository;

    public PortfolioServiceImpl(
            PortfolioRepository portfolioRepository,
            InvestmentRepository investmentRepository,
            UserRepository userRepository
    ) {
        this.portfolioRepository = portfolioRepository;
        this.investmentRepository = investmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PortfolioResponseDto create(Integer userId, PortfolioCreateDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("El usuario no existe"));

        Portfolio portfolio = Portfolio.builder()
                .name(dto.getName())
                .totalBalance(dto.getTotalBalance())
                .riskProfile(dto.getRiskProfile())
                .user(user)
                .build();

        return toResponse(portfolioRepository.save(portfolio));
    }

    @Override
    public List<PortfolioResponseDto> findAllByUser(Integer userId) {
        return portfolioRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public PortfolioPerformanceDto getPerformance(Integer portfolioId, Integer userId) {
        Portfolio portfolio = portfolioRepository.findByIdAndUserId(portfolioId, userId)
                .orElseThrow(() -> new NotFoundException("El portafolio no existe"));

        List<Investment> investments = investmentRepository.findByPortfolioId(portfolio.getId());

        BigDecimal investedAmount = BigDecimal.ZERO;
        BigDecimal currentValue = BigDecimal.ZERO;

        for (Investment investment : investments) {
            BigDecimal invested = investment.getQuantity()
                    .multiply(investment.getPurchasePrice());

            BigDecimal current = investment.getQuantity()
                    .multiply(investment.getAsset().getMarketPrice());

            investedAmount = investedAmount.add(invested);
            currentValue = currentValue.add(current);
        }

        BigDecimal profit = currentValue.subtract(investedAmount);

        return PortfolioPerformanceDto.builder()
                .portfolioId(portfolio.getId())
                .investedAmount(investedAmount)
                .currentValue(currentValue)
                .profit(profit)
                .build();
    }

    private PortfolioResponseDto toResponse(Portfolio portfolio) {
        return PortfolioResponseDto.builder()
                .id(portfolio.getId())
                .name(portfolio.getName())
                .totalBalance(portfolio.getTotalBalance())
                .riskProfile(portfolio.getRiskProfile())
                .build();
    }
}