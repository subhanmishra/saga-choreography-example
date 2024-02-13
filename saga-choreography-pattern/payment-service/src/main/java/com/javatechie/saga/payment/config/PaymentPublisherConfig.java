package com.javatechie.saga.payment.config;

import com.javatechie.saga.commons.event.OrderEvent;
import com.javatechie.saga.commons.event.PaymentEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class PaymentPublisherConfig {

    @Bean
    public Sinks.Many<PaymentEvent> paymentSinks() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<PaymentEvent>> paymentSupplier(Sinks.Many<PaymentEvent> sinks) {
        return sinks::asFlux;
    }
}
