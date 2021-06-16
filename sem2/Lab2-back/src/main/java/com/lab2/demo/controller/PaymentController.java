package com.lab2.demo.controller;

import com.lab2.demo.dto.CardDTO;
import com.lab2.demo.dto.PaymentDTO;
import com.lab2.demo.service.PaymentControllerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public class PaymentController {
    private final PaymentControllerService paymentService;

    @PostMapping(value = "/payment")
    public CardDTO addPayment(@RequestBody PaymentDTO paymentDTO) {
        return paymentService.addPayment(paymentDTO);
    }

    @PostMapping(value = "/replenish")
    public CardDTO addReplenish(@RequestBody PaymentDTO paymentDto) {
        return paymentService.replenishAccount(paymentDto);
    }
}
