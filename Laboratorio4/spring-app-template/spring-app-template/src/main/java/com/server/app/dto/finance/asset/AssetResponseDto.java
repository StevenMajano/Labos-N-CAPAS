package com.server.app.dto.finance.asset;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AssetResponseDto {

    private Integer id;
    private String symbol;
    private String market;
    private BigDecimal marketPrice;
    private String sector;
}