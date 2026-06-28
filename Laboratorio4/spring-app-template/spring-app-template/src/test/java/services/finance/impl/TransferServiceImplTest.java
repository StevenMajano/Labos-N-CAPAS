package services.finance.impl;

import com.server.app.dto.finance.transfer.TransferRequestDto;
import com.server.app.dto.finance.transfer.TransferResponseDto;
import com.server.app.entities.User;
import com.server.app.entities.finance.Account;
import com.server.app.enums.AccountType;
import com.server.app.enums.CurrencyType;
import com.server.app.exceptions.BadRequestException;
import com.server.app.repositories.finance.AccountRepository;
import com.server.app.services.finance.impl.TransferServiceImpl;
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
class TransferServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransferServiceImpl transferService;

    @Test
    void transferFundsShouldTransferMoneyWhenBalanceIsEnough() {
        Integer userId = 1;

        User user = new User();
        user.setId(userId);

        Account originAccount = Account.builder()
                .id(1)
                .alias("Cuenta origen")
                .currency(CurrencyType.USD)
                .baseBalance(BigDecimal.valueOf(1000))
                .type(AccountType.SAVINGS)
                .user(user)
                .build();

        Account destinationAccount = Account.builder()
                .id(2)
                .alias("Cuenta destino")
                .currency(CurrencyType.USD)
                .baseBalance(BigDecimal.valueOf(200))
                .type(AccountType.CHECKING)
                .user(user)
                .build();

        TransferRequestDto dto = new TransferRequestDto();
        dto.setOriginAccountId(1);
        dto.setDestinationAccountId(2);
        dto.setAmount(BigDecimal.valueOf(100));
        dto.setDescription("Transferencia de prueba");

        when(accountRepository.findByIdAndUserId(1, userId)).thenReturn(Optional.of(originAccount));
        when(accountRepository.findByIdAndUserId(2, userId)).thenReturn(Optional.of(destinationAccount));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TransferResponseDto response = transferService.transferFunds(userId, dto);

        assertNotNull(response);
        assertEquals(1, response.getOriginAccountId());
        assertEquals(2, response.getDestinationAccountId());
        assertEquals("Transferencia realizada correctamente", response.getMessage());
        assertEquals(0, BigDecimal.valueOf(900).compareTo(originAccount.getBaseBalance()));
        assertEquals(0, BigDecimal.valueOf(300).compareTo(destinationAccount.getBaseBalance()));

        verify(accountRepository).findByIdAndUserId(1, userId);
        verify(accountRepository).findByIdAndUserId(2, userId);
        verify(accountRepository, times(2)).save(any(Account.class));
    }

    @Test
    void transferFundsShouldThrowBadRequestWhenBalanceIsInsufficient() {
        Integer userId = 1;

        User user = new User();
        user.setId(userId);

        Account originAccount = Account.builder()
                .id(1)
                .alias("Cuenta origen")
                .currency(CurrencyType.USD)
                .baseBalance(BigDecimal.valueOf(50))
                .type(AccountType.SAVINGS)
                .user(user)
                .build();

        Account destinationAccount = Account.builder()
                .id(2)
                .alias("Cuenta destino")
                .currency(CurrencyType.USD)
                .baseBalance(BigDecimal.valueOf(200))
                .type(AccountType.CHECKING)
                .user(user)
                .build();

        TransferRequestDto dto = new TransferRequestDto();
        dto.setOriginAccountId(1);
        dto.setDestinationAccountId(2);
        dto.setAmount(BigDecimal.valueOf(100));

        when(accountRepository.findByIdAndUserId(1, userId)).thenReturn(Optional.of(originAccount));
        when(accountRepository.findByIdAndUserId(2, userId)).thenReturn(Optional.of(destinationAccount));

        assertThrows(BadRequestException.class, () -> transferService.transferFunds(userId, dto));

        verify(accountRepository).findByIdAndUserId(1, userId);
        verify(accountRepository).findByIdAndUserId(2, userId);
        verify(accountRepository, never()).save(any(Account.class));
    }
}