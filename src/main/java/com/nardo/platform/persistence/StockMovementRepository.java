package com.nardo.platform.persistence;

import com.nardo.platform.business.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
