package com.nardo.platform.persistence;

import com.nardo.platform.business.entity.AlertLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertLogRepository extends JpaRepository<AlertLog, Long> {
    List<AlertLog> findByResolvedFalse();
}
