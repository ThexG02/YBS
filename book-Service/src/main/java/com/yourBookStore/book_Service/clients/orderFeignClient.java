package com.yourBookStore.book_Service.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="order",path = "/order")
public interface orderFeignClient {
}
