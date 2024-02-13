package com.subhanmishra.saga.payment.service;

import com.subhanmishra.saga.commons.dto.PaymentRequestDto;
import com.subhanmishra.saga.commons.event.PaymentEvent;
import com.subhanmishra.saga.commons.event.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class PaymentStatusPublisher {

    @Autowired
    Sinks.Many<PaymentEvent> paymentSinks;

    public void publishPaymentEvent(PaymentRequestDto paymentRequestDto, PaymentStatus paymentStatus){
        PaymentEvent paymentEvent = new PaymentEvent(paymentRequestDto, paymentStatus);
        paymentSinks.tryEmitNext(paymentEvent);

    }
}
