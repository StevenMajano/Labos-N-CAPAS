package com.server.app.dto.finance.transfer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferResponseDto {

    private String message;

    private Integer originAccountId;

    private Integer destinationAccountId;
}