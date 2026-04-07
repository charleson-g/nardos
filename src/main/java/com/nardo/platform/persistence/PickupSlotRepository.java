package com.nardo.platform.persistence;

import com.nardo.platform.business.entity.PickupSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface PickupSlotRepository extends JpaRepository<PickupSlot, String> {
    List<PickupSlot> findByIsBookedFalse();
    List<PickupSlot> findByStartTimeAfterAndIsBookedFalse(LocalTime time);
    void deleteByStartTimeBefore(LocalTime time);
}
