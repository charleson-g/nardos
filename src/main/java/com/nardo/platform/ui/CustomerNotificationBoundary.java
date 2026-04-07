package com.nardo.platform.ui;

import org.springframework.stereotype.Service;

/**
 * Acts as the boundary for notifying the customer.
 * In a real application, this would interface with an SMS, Email, or Web Push Notification gateway.
 */
@Service
public class CustomerNotificationBoundary {

    public void notifyOrderReady(String orderID) {
        System.out.println("=================================================");
        System.out.println("[\uD83D\uDD14 CUSTOMER NOTIFICATION]: Your order " + orderID + " is READY for pickup!");
        System.out.println("=================================================");
    }
}
