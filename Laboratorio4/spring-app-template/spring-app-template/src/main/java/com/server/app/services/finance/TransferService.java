package com.server.app.services.finance;

import com.server.app.dto.finance.transfer.TransferRequestDto;
import com.server.app.dto.finance.transfer.TransferResponseDto;

public interface TransferService {

    TransferResponseDto transferFunds(
            Integer userId,
            TransferRequestDto dto
    );
}