package com.yourBookStore.order_Service.Client;

import com.yourBookStore.order_Service.Entity.PaymentDto;
import lombok.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "payment-Service")
public interface PaymentFeignClient {
    @PostMapping("/payments")
    ResponseEntity<String> processPayment(PaymentDto paymentDto);


}
