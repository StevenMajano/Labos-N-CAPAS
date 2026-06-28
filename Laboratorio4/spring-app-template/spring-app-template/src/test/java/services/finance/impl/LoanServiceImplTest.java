package services.finance.impl;

import com.server.app.dto.finance.loan.LoanCreateDto;
import com.server.app.dto.finance.loan.LoanResponseDto;
import com.server.app.entities.User;
import com.server.app.entities.finance.Loan;
import com.server.app.entities.finance.PaymentPlan;
import com.server.app.enums.finance.LoanStatus;
import com.server.app.exceptions.BadRequestException;
import com.server.app.repositories.UserRepository;
import com.server.app.repositories.finance.LoanRepository;
import com.server.app.repositories.finance.PaymentPlanRepository;
import com.server.app.services.finance.impl.LoanServiceImpl;
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
class LoanServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private PaymentPlanRepository paymentPlanRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    @Test
    void createShouldSaveLoanAndGeneratePaymentPlan() {
        Integer userId = 1;

        User user = new User();
        user.setId(userId);

        LoanCreateDto dto = new LoanCreateDto();
        dto.setRequestedCapital(BigDecimal.valueOf(1200));
        dto.setAnnualInterestRate(BigDecimal.valueOf(12));
        dto.setTermMonths(12);

        Loan savedLoan = Loan.builder()
                .id(1)
                .requestedCapital(dto.getRequestedCapital())
                .annualInterestRate(dto.getAnnualInterestRate())
                .termMonths(dto.getTermMonths())
                .status(LoanStatus.APPROVED)
                .user(user)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(loanRepository.save(any(Loan.class))).thenReturn(savedLoan);
        when(paymentPlanRepository.save(any(PaymentPlan.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        LoanResponseDto response = loanService.create(userId, dto);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals(LoanStatus.APPROVED, response.getStatus());
        assertEquals(BigDecimal.valueOf(1200), response.getRequestedCapital());
        assertEquals(12, response.getTermMonths());

        verify(userRepository).findById(userId);
        verify(loanRepository).save(any(Loan.class));
        verify(paymentPlanRepository, times(12)).save(any(PaymentPlan.class));
    }

    @Test
    void createShouldThrowBadRequestWhenTermMonthsIsInvalid() {
        Integer userId = 1;

        LoanCreateDto dto = new LoanCreateDto();
        dto.setRequestedCapital(BigDecimal.valueOf(1200));
        dto.setAnnualInterestRate(BigDecimal.valueOf(12));
        dto.setTermMonths(0);

        assertThrows(BadRequestException.class, () -> loanService.create(userId, dto));

        verify(userRepository, never()).findById(anyInt());
        verify(loanRepository, never()).save(any(Loan.class));
        verify(paymentPlanRepository, never()).save(any(PaymentPlan.class));
    }
}