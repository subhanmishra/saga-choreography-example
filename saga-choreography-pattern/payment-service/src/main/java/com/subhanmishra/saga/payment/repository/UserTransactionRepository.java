package com.subhanmishra.saga.payment.repository;

import com.subhanmishra.saga.payment.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTransactionRepository extends JpaRepository<UserTransaction,Integer> {
}
