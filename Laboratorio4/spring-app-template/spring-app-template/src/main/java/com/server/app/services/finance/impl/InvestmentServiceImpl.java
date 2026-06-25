package com.server.app.services.finance.impl;

import com.server.app.dto.finance.investment.InvestmentCreateDto;
import com.server.app.dto.finance.investment.InvestmentResponseDto;
import com.server.app.entities.finance.Asset;
import com.server.app.entities.finance.Investment;
import com.server.app.entities.finance.Portfolio;
import com.server.app.enums.InvestmentStatus;
import com.server.app.exceptions.NotFoundException;
import com.server.app.repositories.finance.AssetRepository;
import com.server.app.repositories.finance.InvestmentRepository;
import com.server.app.repositories.finance.PortfolioRepository;
import com.server.app.services.finance.InvestmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InvestmentServiceImpl implements InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final PortfolioRepository portfolioRepository;
    private final AssetRepository assetRepository;

    public InvestmentServiceImpl(
            InvestmentRepository investmentRepository,
            PortfolioRepository portfolioRepository,
            AssetRepository assetRepository
    ) {
        this.investmentRepository = investmentRepository;
        this.portfolioRepository = portfolioRepository;
        this.assetRepository = assetRepository;
    }

    @Override
    public InvestmentResponseDto create(Integer userId, InvestmentCreateDto dto) {
        Portfolio portfolio = portfolioRepository.findByIdAndUserId(dto.getPortfolioId(), userId)
                .orElseThrow(() -> new NotFoundException("El portafolio no existe"));

        Asset asset = assetRepository.findById(dto.getAssetId())
                .orElseThrow(() -> new NotFoundException("El activo no existe"));

        Investment investment = Investment.builder()
                .quantity(dto.getQuantity())
                .purchasePrice(dto.getPurchasePrice())
                .date(LocalDateTime.now())
                .status(InvestmentStatus.OPEN)
                .portfolio(portfolio)
                .asset(asset)
                .build();

        return toResponse(investmentRepository.save(investment));
    }

    private InvestmentResponseDto toResponse(Investment investment) {
        return InvestmentResponseDto.builder()
                .id(investment.getId())
                .quantity(investment.getQuantity())
                .purchasePrice(investment.getPurchasePrice())
                .date(investment.getDate())
                .status(investment.getStatus())
                .portfolioId(investment.getPortfolio().getId())
                .assetId(investment.getAsset().getId())
                .build();
    }
}