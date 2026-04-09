package com.nardo.platform.persistence;

import com.nardo.platform.business.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, String> {
    List<Sale> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
