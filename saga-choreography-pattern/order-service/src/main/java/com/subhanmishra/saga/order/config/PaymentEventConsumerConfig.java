package com.subhanmishra.saga.order.config;

import com.subhanmishra.saga.commons.event.PaymentEvent;
import com.subhanmishra.saga.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class PaymentEventConsumerConfig {

    @Autowired
    private OrderService orderService;


    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer() {

        return (payment) -> orderService.updateOrder(payment.getPaymentRequestDto().getOrderId(), po ->
                po.setPaymentStatus(payment.getPaymentStatus()));
    }
}
