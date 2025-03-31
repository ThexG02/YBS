package com.yourBookStore.book_Service.config;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Resillience4jconfig {
    @CircuitBreaker(name = "book-Service", fallbackMethod = "fallbackBook")
    @Retry(name = "book-Service",fallbackMethod = "fallbackBook")
    public String fallbackBook(){
        return  "Book Service is currently unavailable , Please try again later";
    }
}
