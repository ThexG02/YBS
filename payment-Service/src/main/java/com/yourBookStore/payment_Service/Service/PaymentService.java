package com.yourBookStore.payment_Service.Service;

import com.yourBookStore.payment_Service.Client.OrderFeignClient;
import com.yourBookStore.payment_Service.Dto.PaymentDto;
import com.yourBookStore.payment_Service.Entity.Payment;
import com.yourBookStore.payment_Service.Repository.PaymentRepository;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderFeignClient orderFeignClient;

    private static final String CIRCUIT_BREAKER_NAME = "paymentService";

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME,fallbackMethod = "paymentFallback")
    @Retry(name = CIRCUIT_BREAKER_NAME,fallbackMethod = "paymentFallBack")

    public Payment processPayment(PaymentDto paymentDto) {

        // Create Payment entity
        Payment payment = new Payment();
        payment.setPrice(paymentDto.getPrice());
        payment.setStatus("Pending");
        payment.setCreatedAt(LocalDateTime.now());
        payment.setOrderId(paymentDto.getOrderId());  // Map orderId separately

        // Save the initial payment
        Payment savedPayment = paymentRepository.save(payment);

        try {
            // Update order status using Feign
            orderFeignClient.updateOrderStatus(paymentDto.getOrderId(), "Paid");
            savedPayment.setStatus("Completed");
        } catch (FeignException e) {  // Catch only Feign exceptions
            savedPayment.setStatus("Failed");
        }

        //  Save the final status
        return paymentRepository.save(savedPayment);
    }

    public Payment getPayment(Long id){
        return paymentRepository.findById(id).orElseThrow(()->new RuntimeException("payment not found"));
    }

}
