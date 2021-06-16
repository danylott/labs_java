package com.lab2.demo.service;

import com.lab2.demo.converter.CardConverter;
import com.lab2.demo.service.data.PaymentService;
import com.lab2.demo.dto.CardDTO;
import com.lab2.demo.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentControllerService {
    private final PaymentService paymentService;
    private final CardConverter cardConverter;

    public CardDTO addPayment(PaymentDTO paymentDTO) {
        return cardConverter.convertToDTO(paymentService.addPayment(paymentDTO));
    }

    public CardDTO replenishAccount(PaymentDTO paymentDTO) {
        return cardConverter.convertToDTO(paymentService.replenishAccount(paymentDTO));
    }
}
