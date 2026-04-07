package com.nardo.platform.business.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalTime;

@Entity
public class PickupSlot {

    @Id
    private String slotID;
    private LocalTime startTime;
    private LocalTime endTime;
    private int maxCapacity;
    private int currentOrders;
    private boolean isBooked;

    public PickupSlot() {}

    public PickupSlot(String slotID, LocalTime start, int capacity) {
        this.slotID = slotID;
        this.startTime = start;
        this.endTime = start.plusMinutes(15);
        this.maxCapacity = capacity;
        this.currentOrders = 0;
        this.isBooked = false;
    }

    public boolean isAvailable() {
        return currentOrders < maxCapacity && !isBooked;
    }

    public void incrementOrders() {
        this.currentOrders++;
        if (this.currentOrders >= maxCapacity) {
            this.isBooked = true;
        }
    }

    public String getSlotID() { return slotID; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    
    public boolean isBooked() { return isBooked; }
    public void setBooked(boolean booked) { this.isBooked = booked; }
}
