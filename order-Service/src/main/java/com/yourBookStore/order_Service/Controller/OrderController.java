package com.yourBookStore.order_Service.Controller;

import com.yourBookStore.order_Service.Dto.OrderDto;
import com.yourBookStore.order_Service.Entity.Order;
import com.yourBookStore.order_Service.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //placeOrder
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderDto orderDto){
        Order order = orderService.placeOrder(orderDto);
        return ResponseEntity.ok(order);
    }

    //get order by id
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id){
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    //cancel order by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id){
        orderService.cancelOrder(id);
        return ResponseEntity.ok("Order with id "+id+ " cancelled Successfully");
    }
}
