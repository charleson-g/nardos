package com.nardo.platform.business.control;
import com.nardo.platform.business.entity.AlertLog;
import com.nardo.platform.business.entity.Order;
import org.springframework.stereotype.Service;

@Service
public class NotificationController {
    public void sendLowStockAlert(AlertLog log) {
        System.out.println("Low Stock Alert: " + log.getMessage());
    }
    public void sendOrderConfirmation(Order order) {
        System.out.println("Order Confirmed: " + order.getOrderId());
    }
}
