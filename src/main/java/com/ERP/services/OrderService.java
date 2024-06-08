package com.ERP.services;


import com.ERP.dtos.OrderDto;
import com.ERP.entities.Order;
import com.ERP.repositories.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
      this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {

//        Order order = new Order();
//        BeanUtils.copyProperties(orderDto,order);
        orderRepository.save(order);
        return order;
    }

    public boolean deleteOrder(Long id) {
        Order order = orderRepository.findById(id).get();
        orderRepository.delete(order);
        return true;
    }


    public List<Order> fetchOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders;
    }


    public Order fetchOrderById(Long id) {
         Optional<Order> order = orderRepository.findById(id);
         if(order.isPresent()) {
             OrderDto orderDto = new OrderDto();
             BeanUtils.copyProperties(order.get(), orderDto);
             return order.get();
         }
         else throw new NoSuchElementException();
    }


    public Order updateOrder(Long id, Order orderDto) {
      Order order = orderRepository.findById(id).get();

      if(Objects.nonNull(orderDto.getAddress()) && !"".equalsIgnoreCase(orderDto.getAddress()))
      {
          order.setAddress(orderDto.getAddress());
      }

      if(Objects.nonNull(orderDto.getOrderDetail()) && !"".equalsIgnoreCase(orderDto.getOrderDetail()))
      {
          order.setOrderDetail(orderDto.getOrderDetail());
      }

      if(Objects.nonNull(orderDto.getPrice()) && !"".equalsIgnoreCase(orderDto.getPrice()))
      {
          order.setPrice(orderDto.getPrice());
      }

      orderRepository.save(order);

      return orderDto;
    }
}
