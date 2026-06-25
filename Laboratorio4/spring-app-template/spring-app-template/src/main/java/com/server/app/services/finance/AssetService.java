package com.server.app.services.finance;

import com.server.app.dto.finance.asset.AssetResponseDto;

import java.util.List;

public interface AssetService {

    List<AssetResponseDto> findAll();
}