package com.subhanmishra.saga.order.repository;

import com.subhanmishra.saga.order.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<PurchaseOrder,Integer> {
}
