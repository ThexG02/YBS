package com.yourBookStore.payment_Service.Controller;

import com.yourBookStore.payment_Service.Dto.PaymentDto;
import com.yourBookStore.payment_Service.Entity.Payment;
import com.yourBookStore.payment_Service.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/payments")

public class PaymentController {

    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping
    public ResponseEntity<Payment> processPayment(@RequestBody PaymentDto paymentDto){
        Payment payment = paymentService.processPayment(paymentDto);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id){
        Payment payment = paymentService.getPayment(id);
        return ResponseEntity.ok(payment);
    }


}
