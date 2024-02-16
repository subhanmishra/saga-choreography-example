package com.subhanmishra.saga.payment.config;

import com.subhanmishra.saga.commons.event.OrderEvent;
import com.subhanmishra.saga.payment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class OrderEventConsumerConfig {

    private final PaymentService paymentService;

    public OrderEventConsumerConfig(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Bean
    public Consumer<OrderEvent> orderEventConsumer() {
        return paymentService::newOrderEvent;
    }

//    @Bean
//    public Function<Flux<OrderEvent>, Flux<PaymentEvent>> paymentProcessor() {
//        return orderEventFlux -> orderEventFlux.flatMap(this::processPayment);
//    }
//
//    private Mono<PaymentEvent> processPayment(OrderEvent orderEvent) {
//        // get the user id
//        // check the balance availability
//        // if balance sufficient -> Payment completed and deduct amount from DB
//        // if payment not sufficient -> cancel order event and update the amount in DB
//        if(OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus())){
//            return  Mono.fromSupplier(()->this.paymentService.newOrderEvent(orderEvent));
//        }else{
//            //return Mono.fromRunnable(()->this.paymentService.cancelOrderEvent(orderEvent));
//            return Mono.empty();
//        }
//    }
}
