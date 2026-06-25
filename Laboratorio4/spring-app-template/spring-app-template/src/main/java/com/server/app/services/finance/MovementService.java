package com.server.app.services.finance;

import com.server.app.dto.finance.movement.MovementCreateDto;
import com.server.app.dto.finance.movement.MovementResponseDto;

import java.util.List;

public interface MovementService {

    MovementResponseDto create(
            Integer userId,
            MovementCreateDto dto
    );

    List<MovementResponseDto> findAllByUser(
            Integer userId
    );

    MovementResponseDto findById(
            Integer movementId,
            Integer userId
    );
}