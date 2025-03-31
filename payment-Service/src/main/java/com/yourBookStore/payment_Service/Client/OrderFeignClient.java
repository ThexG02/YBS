package com.yourBookStore.payment_Service.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-Service" ,url = "http://order-Service/order")
public interface OrderFeignClient {
    @PostMapping("/{id}/status")
    public void updateOrderStatus(@PathVariable("id") Long orderID, @RequestParam("status") String status) ;
}
