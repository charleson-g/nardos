package com.nardo.platform.persistence;

import com.nardo.platform.business.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByOrderTimestampBetween(LocalDateTime start, LocalDateTime end);
}
