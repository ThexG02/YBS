package com.yourBookStore.order_Service.Service;

import com.yourBookStore.order_Service.Client.PaymentFeignClient;
import com.yourBookStore.order_Service.Dto.OrderDto;
import com.yourBookStore.order_Service.Entity.Order;
import com.yourBookStore.order_Service.Entity.PaymentDto;
import com.yourBookStore.order_Service.Repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentFeignClient paymentFeignClient;

    private static final String CIRCUIT_BREAKER_NAME= "paymentService";



    //place order
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME,fallbackMethod = "paymentFallback")
    @Retry(name = CIRCUIT_BREAKER_NAME,fallbackMethod = "paymentFallback")
    public Order placeOrder(OrderDto orderDto){

        //create order
        Order order = new Order();
        order.setAmount(orderDto.getAmount());
        order.setStatus("Pending");
        order.setCreatedAt(LocalDateTime.now());
        order.setItem(orderDto.getItem());


        //save initial order
        Order savedOrder = orderRepository.save(order);

        //Preparing Payment DTO
        PaymentDto paymentDto = new PaymentDto(orderDto.getAmount(),savedOrder.getId().toString());

        try{
            //usage of feign-client to call Payment Service
            paymentFeignClient.processPayment(paymentDto);
            savedOrder.setStatus("Completed");
        }
        catch (Exception e){
            savedOrder.setStatus("Failed");
        }

        //Update and Save final order
        return orderRepository.save(savedOrder);

    }


    //fetch order

    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElseThrow(()-> new RuntimeException("Order not found"));
    }

    //cancel order

    public void cancelOrder(Long id){
         orderRepository.deleteById(id);
    }

    //FallBack Method for the resilience
    public Order paymentFallback(OrderDto orderDto , Exception e){
        Order order= new Order();
        order.setItem(orderDto.getItem());
        order.setAmount(orderDto.getAmount());
        order.setStatus("Failed");
        order.setCreatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

}
