package com.yourBookStore.payment_Service.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private Double price;
    private String status;
    private Long orderId;
}
