package com.subhanmishra.saga.order.controller;

import com.subhanmishra.saga.commons.dto.OrderRequestDto;
import com.subhanmishra.saga.order.entity.PurchaseOrder;
import com.subhanmishra.saga.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping()
    public PurchaseOrder createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }

    @GetMapping
    public List<PurchaseOrder> getOrders() {
        return orderService.getAllOrders();
    }
}
