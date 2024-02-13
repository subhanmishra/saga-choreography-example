package com.javatechie.saga.payment.service;

import com.javatechie.saga.commons.dto.OrderRequestDto;
import com.javatechie.saga.commons.dto.PaymentRequestDto;
import com.javatechie.saga.commons.event.OrderEvent;
import com.javatechie.saga.commons.event.OrderStatus;
import com.javatechie.saga.commons.event.PaymentStatus;
import com.javatechie.saga.payment.entity.UserBalance;
import com.javatechie.saga.payment.entity.UserTransaction;
import com.javatechie.saga.payment.repository.UserBalanceRepository;
import com.javatechie.saga.payment.repository.UserTransactionRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    PaymentStatusPublisher paymentStatusPublisher;
    @Autowired
    private UserBalanceRepository userBalanceRepository;
    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @PostConstruct
    public void initUserBalanceInDB() {
        userBalanceRepository.saveAll(Stream.of(new UserBalance(101, 5000),
                new UserBalance(102, 3000),
                new UserBalance(103, 4200),
                new UserBalance(104, 20000),
                new UserBalance(105, 999)).collect(Collectors.toList()));
    }

    /**
     * // get the user id
     * // check the balance availability
     * // if balance sufficient -> Payment completed and deduct amount from DB
     * // if payment not sufficient -> cancel order event and update the amount in DB
     **/
    @Transactional
    public void newOrderEvent(OrderEvent orderEvent) {
        log.info("Order status {}", orderEvent.getOrderStatus().name());
        if(orderEvent.getOrderStatus().equals(OrderStatus.ORDER_CREATED)) {
            OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();

            PaymentRequestDto paymentRequestDto = new PaymentRequestDto(orderRequestDto.getOrderId(),
                    orderRequestDto.getUserId(), orderRequestDto.getAmount());

            userBalanceRepository.findById(orderRequestDto.getUserId())
                    .filter(ub -> ub.getBalance() > orderRequestDto.getAmount())
                    .ifPresentOrElse(ub -> savePaymentAndCreatePaymentCompletedEvent(ub, orderRequestDto, paymentRequestDto), () -> paymentStatusPublisher.publishPaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED));
        }

    }
    private void savePaymentAndCreatePaymentCompletedEvent(UserBalance ub, OrderRequestDto orderRequestDto, PaymentRequestDto paymentRequestDto) {
        log.info("User balance: {}", ub.getBalance());
        ub.setBalance(ub.getBalance() - orderRequestDto.getAmount());
        userTransactionRepository.save(new UserTransaction(orderRequestDto.getOrderId(), orderRequestDto.getUserId(), orderRequestDto.getAmount()));
        paymentStatusPublisher.publishPaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
    }

//    @Transactional
//    public void cancelOrderEvent(OrderEvent orderEvent) {
//
//        userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId())
//                .ifPresent(ut -> {
//                    userTransactionRepository.delete(ut);
//                    userTransactionRepository.findById(ut.getUserId())
//                            .ifPresent(ub -> ub.setAmount(ub.getAmount() + ut.getAmount()));
//                });
//    }
}
