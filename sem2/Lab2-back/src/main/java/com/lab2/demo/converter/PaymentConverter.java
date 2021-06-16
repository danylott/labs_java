package com.lab2.demo.converter;

import com.lab2.demo.dto.PaymentDTO;
import com.lab2.demo.entity.Card;
import com.lab2.demo.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentConverter {
    public PaymentDTO convertToDto(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setCardFrom(payment.getFrom().getId());
        paymentDTO.setCardTo(payment.getTo().getId());

        return paymentDTO;
    }

    public Payment convertToEntity(PaymentDTO paymentDTO, Card from, Card to) {
        Payment payment = new Payment();

        payment.setAmount(paymentDTO.getAmount());
        payment.setFrom(from);
        payment.setTo(to);

        return payment;
    }

    public List<PaymentDTO> convertToListDto(List<Payment> payments) {
        return payments.stream().
                map(this::convertToDto)
                .collect(Collectors.toList());
    }
}