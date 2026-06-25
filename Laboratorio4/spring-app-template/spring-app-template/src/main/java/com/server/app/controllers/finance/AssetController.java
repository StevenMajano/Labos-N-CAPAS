package com.server.app.controllers.finance;

import com.server.app.dto.finance.asset.AssetResponseDto;
import com.server.app.services.finance.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    public ResponseEntity<List<AssetResponseDto>> findAll() {
        return ResponseEntity.ok(assetService.findAll());
    }
}