package com.server.app.controllers.finance;

import com.server.app.dto.finance.transfer.TransferRequestDto;
import com.server.app.dto.finance.transfer.TransferResponseDto;
import com.server.app.services.finance.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.server.app.utils.AuthUtil.getCurrentUserId;

@RestController
@RequestMapping("/api/finance/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<TransferResponseDto> transfer(
            @Valid @RequestBody TransferRequestDto dto
    ) {
        Integer userId = getCurrentUserId();

        return ResponseEntity.ok(
                transferService.transferFunds(userId, dto)
        );
    }
}