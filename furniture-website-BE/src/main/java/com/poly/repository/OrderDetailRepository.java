package com.poly.repository;

import com.poly.entity.OrderDetail;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
