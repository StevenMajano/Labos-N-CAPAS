package services.finance.impl;

import com.server.app.dto.finance.account.AccountCreateDto;
import com.server.app.dto.finance.account.AccountResponseDto;
import com.server.app.entities.User;
import com.server.app.entities.finance.Account;
import com.server.app.enums.AccountType;
import com.server.app.enums.CurrencyType;
import com.server.app.exceptions.BadRequestException;
import com.server.app.repositories.UserRepository;
import com.server.app.repositories.finance.AccountRepository;
import com.server.app.services.finance.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void createShouldSaveAccountWhenAliasDoesNotExist() {
        Integer userId = 1;

        User user = new User();
        user.setId(userId);

        AccountCreateDto dto = new AccountCreateDto();
        dto.setAlias("Cuenta principal");
        dto.setCurrency(CurrencyType.USD);
        dto.setBaseBalance(BigDecimal.valueOf(1000));
        dto.setType(AccountType.SAVINGS);

        Account savedAccount = Account.builder()
                .id(1)
                .alias(dto.getAlias())
                .currency(dto.getCurrency())
                .baseBalance(dto.getBaseBalance())
                .type(dto.getType())
                .user(user)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(accountRepository.existsByAliasAndUserId(dto.getAlias(), userId)).thenReturn(false);
        when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);

        AccountResponseDto response = accountService.create(userId, dto);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Cuenta principal", response.getAlias());
        assertEquals(CurrencyType.USD, response.getCurrency());
        assertEquals(AccountType.SAVINGS, response.getType());

        verify(userRepository).findById(userId);
        verify(accountRepository).existsByAliasAndUserId(dto.getAlias(), userId);
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void createShouldThrowBadRequestWhenAliasAlreadyExists() {
        Integer userId = 1;

        User user = new User();
        user.setId(userId);

        AccountCreateDto dto = new AccountCreateDto();
        dto.setAlias("Cuenta principal");
        dto.setCurrency(CurrencyType.USD);
        dto.setBaseBalance(BigDecimal.valueOf(1000));
        dto.setType(AccountType.SAVINGS);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(accountRepository.existsByAliasAndUserId(dto.getAlias(), userId)).thenReturn(true);

        assertThrows(BadRequestException.class, () -> accountService.create(userId, dto));

        verify(userRepository).findById(userId);
        verify(accountRepository).existsByAliasAndUserId(dto.getAlias(), userId);
        verify(accountRepository, never()).save(any(Account.class));
    }
}