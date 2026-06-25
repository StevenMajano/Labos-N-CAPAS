package com.server.app.controllers.finance;

import com.server.app.dto.finance.account.AccountCreateDto;
import com.server.app.dto.finance.account.AccountResponseDto;
import com.server.app.dto.finance.account.AccountUpdateDto;
import com.server.app.services.finance.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.server.app.utils.AuthUtil.getCurrentUserId;

@RestController
@RequestMapping("/api/finance/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponseDto> create(
            @Valid @RequestBody AccountCreateDto dto
    ) {
        Integer userId = getCurrentUserId();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.create(userId, dto));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDto>> findAll() {
        Integer userId = getCurrentUserId();
        return ResponseEntity.ok(accountService.findAllByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> findById(
            @PathVariable Integer id
    ) {
        Integer userId = getCurrentUserId();
        return ResponseEntity.ok(accountService.findById(id, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDto> update(
            @PathVariable Integer id,
            @Valid @RequestBody AccountUpdateDto dto
    ) {
        Integer userId = getCurrentUserId();
        return ResponseEntity.ok(accountService.update(id, userId, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {
        Integer userId = getCurrentUserId();
        accountService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}