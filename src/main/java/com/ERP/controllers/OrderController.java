package com.ERP.controllers;


import com.ERP.entities.Order;
import com.ERP.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    public OrderService orderService;

     @PostMapping("/add")
     public ResponseEntity<Order>createOrder(@RequestBody Order order)
     {
         return ResponseEntity.ok(orderService.createOrder(order));
     }

     @DeleteMapping("/deleteById/{id}")
     public ResponseEntity<Map<String,Boolean>>deleteOrder(@PathVariable Long id )
     {

         boolean deleted = false;
         deleted = orderService.deleteOrder(id);
         Map<String,Boolean>response = new HashMap<>();
         response.put("deleted",deleted);
          return ResponseEntity.ok(response);
     }


    @GetMapping("/find")
    public ResponseEntity<List<Order>> fetchOrders()
    {
      return  ResponseEntity.ok(orderService.fetchOrders());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Order>fetchOrderById(@PathVariable Long id)
    {
        return ResponseEntity.ok(orderService.fetchOrderById(id));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable ("id") Long id, @RequestBody Order orderDto)
    {
        orderDto = orderService.updateOrder(id,orderDto);
        return ResponseEntity.ok(orderDto);
    }


}
