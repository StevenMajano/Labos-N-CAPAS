package com.server.app.services.finance.impl;

import com.server.app.dto.finance.loan.CreditSummaryDto;
import com.server.app.dto.finance.loan.LoanCreateDto;
import com.server.app.dto.finance.loan.LoanResponseDto;
import com.server.app.dto.finance.paymentplan.PaymentPlanResponseDto;
import com.server.app.entities.User;
import com.server.app.entities.finance.Loan;
import com.server.app.entities.finance.PaymentPlan;
import com.server.app.enums.finance.LoanStatus;
import com.server.app.enums.finance.PaymentPlanStatus;
import com.server.app.exceptions.BadRequestException;
import com.server.app.exceptions.NotFoundException;
import com.server.app.repositories.UserRepository;
import com.server.app.repositories.finance.LoanRepository;
import com.server.app.repositories.finance.PaymentPlanRepository;
import com.server.app.services.finance.LoanService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final PaymentPlanRepository paymentPlanRepository;
    private final UserRepository userRepository;

    public LoanServiceImpl(
            LoanRepository loanRepository,
            PaymentPlanRepository paymentPlanRepository,
            UserRepository userRepository
    ) {
        this.loanRepository = loanRepository;
        this.paymentPlanRepository = paymentPlanRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public LoanResponseDto create(Integer userId, LoanCreateDto dto) {
        if (dto.getTermMonths() <= 0) {
            throw new BadRequestException("El plazo debe ser mayor a cero");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("El usuario no existe"));

        Loan loan = Loan.builder()
                .requestedCapital(dto.getRequestedCapital())
                .annualInterestRate(dto.getAnnualInterestRate())
                .termMonths(dto.getTermMonths())
                .status(LoanStatus.APPROVED)
                .user(user)
                .build();

        Loan savedLoan = loanRepository.save(loan);

        generatePaymentPlan(savedLoan);

        return toLoanResponse(savedLoan);
    }

    @Override
    public List<LoanResponseDto> findAllByUser(Integer userId) {
        return loanRepository.findByUserId(userId)
                .stream()
                .map(this::toLoanResponse)
                .toList();
    }

    @Override
    public List<PaymentPlanResponseDto> findPaymentPlans(Integer loanId, Integer userId) {
        Loan loan = loanRepository.findByIdAndUserId(loanId, userId)
                .orElseThrow(() -> new NotFoundException("El préstamo no existe"));

        return paymentPlanRepository.findByLoanIdOrderByInstallmentNumberAsc(loan.getId())
                .stream()
                .map(this::toPaymentPlanResponse)
                .toList();
    }

    @Override
    public CreditSummaryDto getCreditSummary(Integer userId) {
        List<Loan> loans = loanRepository.findByUserId(userId);

        BigDecimal totalRequestedCapital = loans.stream()
                .map(Loan::getRequestedCapital)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPendingCapital = paymentPlanRepository.findAll()
                .stream()
                .filter(plan -> plan.getLoan().getUser().getId() == userId)
                .filter(plan -> plan.getStatus() == PaymentPlanStatus.PENDING)
                .map(PaymentPlan::getCapitalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CreditSummaryDto.builder()
                .totalLoans(loans.size())
                .totalRequestedCapital(totalRequestedCapital)
                .totalPendingCapital(totalPendingCapital)
                .build();
    }

    private void generatePaymentPlan(Loan loan) {
        BigDecimal monthlyCapital = loan.getRequestedCapital()
                .divide(BigDecimal.valueOf(loan.getTermMonths()), 2, RoundingMode.HALF_UP);

        BigDecimal monthlyInterestRate = loan.getAnnualInterestRate()
                .divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), 6, RoundingMode.HALF_UP);

        for (int i = 1; i <= loan.getTermMonths(); i++) {
            BigDecimal interestAmount = loan.getRequestedCapital()
                    .multiply(monthlyInterestRate)
                    .setScale(2, RoundingMode.HALF_UP);

            PaymentPlan paymentPlan = PaymentPlan.builder()
                    .installmentNumber(i)
                    .capitalAmount(monthlyCapital)
                    .interestAmount(interestAmount)
                    .dueDate(LocalDate.now().plusMonths(i))
                    .status(PaymentPlanStatus.PENDING)
                    .loan(loan)
                    .build();

            paymentPlanRepository.save(paymentPlan);
        }
    }

    private LoanResponseDto toLoanResponse(Loan loan) {
        return LoanResponseDto.builder()
                .id(loan.getId())
                .requestedCapital(loan.getRequestedCapital())
                .annualInterestRate(loan.getAnnualInterestRate())
                .termMonths(loan.getTermMonths())
                .status(loan.getStatus())
                .build();
    }

    private PaymentPlanResponseDto toPaymentPlanResponse(PaymentPlan paymentPlan) {
        return PaymentPlanResponseDto.builder()
                .id(paymentPlan.getId())
                .installmentNumber(paymentPlan.getInstallmentNumber())
                .capitalAmount(paymentPlan.getCapitalAmount())
                .interestAmount(paymentPlan.getInterestAmount())
                .dueDate(paymentPlan.getDueDate())
                .status(paymentPlan.getStatus())
                .build();
    }
}