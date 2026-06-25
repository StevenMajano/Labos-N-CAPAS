package com.server.app.services.finance.impl;

import com.server.app.dto.finance.payment.PaymentCreateDto;
import com.server.app.dto.finance.payment.PaymentResponseDto;
import com.server.app.entities.finance.Payment;
import com.server.app.entities.finance.PaymentPlan;
import com.server.app.enums.finance.PaymentPlanStatus;
import com.server.app.exceptions.BadRequestException;
import com.server.app.exceptions.NotFoundException;
import com.server.app.repositories.finance.PaymentPlanRepository;
import com.server.app.repositories.finance.PaymentRepository;
import com.server.app.services.finance.PaymentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentPlanRepository paymentPlanRepository;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            PaymentPlanRepository paymentPlanRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.paymentPlanRepository = paymentPlanRepository;
    }

    @Override
    @Transactional
    public PaymentResponseDto create(Integer userId, PaymentCreateDto dto) {
        PaymentPlan paymentPlan = paymentPlanRepository.findById(dto.getPaymentPlanId())
                .orElseThrow(() -> new NotFoundException("El plan de pago no existe"));

        if (paymentPlan.getLoan().getUser().getId() != userId) {
            throw new NotFoundException("El plan de pago no existe");
        }

        if (paymentPlan.getStatus() == PaymentPlanStatus.PAID) {
            throw new BadRequestException("La cuota ya fue pagada");
        }

        BigDecimal requiredAmount = paymentPlan.getCapitalAmount()
                .add(paymentPlan.getInterestAmount());

        if (dto.getAmount().compareTo(requiredAmount) < 0) {
            throw new BadRequestException("El monto pagado es menor al monto requerido");
        }

        BigDecimal lateFee = BigDecimal.ZERO;

        if (LocalDate.now().isAfter(paymentPlan.getDueDate())) {
            lateFee = requiredAmount.multiply(BigDecimal.valueOf(0.05));
        }

        Payment payment = Payment.builder()
                .amount(dto.getAmount())
                .paymentDate(LocalDate.now())
                .lateFee(lateFee)
                .paymentPlan(paymentPlan)
                .build();

        paymentPlan.setStatus(PaymentPlanStatus.PAID);
        paymentPlanRepository.save(paymentPlan);

        return toResponse(paymentRepository.save(payment));
    }

    private PaymentResponseDto toResponse(Payment payment) {
        return PaymentResponseDto.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .lateFee(payment.getLateFee())
                .paymentPlanId(payment.getPaymentPlan().getId())
                .build();
    }
}