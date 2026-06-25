package com.server.app.services.finance.impl;

import com.server.app.dto.finance.asset.AssetResponseDto;
import com.server.app.entities.finance.Asset;
import com.server.app.repositories.finance.AssetRepository;
import com.server.app.services.finance.AssetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;

    public AssetServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Override
    public List<AssetResponseDto> findAll() {
        return assetRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private AssetResponseDto toResponse(Asset asset) {
        return AssetResponseDto.builder()
                .id(asset.getId())
                .symbol(asset.getSymbol())
                .market(asset.getMarket())
                .marketPrice(asset.getMarketPrice())
                .sector(asset.getSector())
                .build();
    }
}