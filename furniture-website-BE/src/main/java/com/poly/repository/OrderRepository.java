package com.poly.repository;

import com.poly.entity.Account;
import com.poly.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByAccount(Account account);
}
