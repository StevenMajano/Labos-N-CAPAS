package com.server.app.controllers.finance;

import com.server.app.dto.finance.movement.MovementCreateDto;
import com.server.app.dto.finance.movement.MovementResponseDto;
import com.server.app.services.finance.MovementService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.server.app.utils.AuthUtil.getCurrentUserId;

@RestController
@RequestMapping("/api/finance/movements")
public class MovementController {

    private final MovementService movementService;

    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @PostMapping
    public ResponseEntity<MovementResponseDto> create(
            @Valid @RequestBody MovementCreateDto dto
    ) {
        Integer userId = getCurrentUserId();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(movementService.create(userId, dto));
    }

    @GetMapping
    public ResponseEntity<List<MovementResponseDto>> findAll() {
        Integer userId = getCurrentUserId();

        return ResponseEntity.ok(
                movementService.findAllByUser(userId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovementResponseDto> findById(
            @PathVariable Integer id
    ) {
        Integer userId = getCurrentUserId();

        return ResponseEntity.ok(
                movementService.findById(id, userId)
        );
    }
}