package com.nardo.platform.business.control;

import com.nardo.platform.business.entity.AlertLog;
import com.nardo.platform.business.entity.Product;
import com.nardo.platform.persistence.AlertLogRepository;
import com.nardo.platform.persistence.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockMonitorController {

    private final InventoryRepository inventoryRepo;
    private final AlertLogRepository alertLogRepo;
    private final NotificationController notifier;

    private static final int THRESHOLD = 10;

    @Autowired
    public StockMonitorController(InventoryRepository inventoryRepo, AlertLogRepository alertLogRepo, NotificationController notifier) {
        this.inventoryRepo = inventoryRepo;
        this.alertLogRepo = alertLogRepo;
        this.notifier = notifier;
    }

    public List<AlertLog> getActiveAlerts() {
        return alertLogRepo.findByResolvedFalse();
    }

    public void resolveAlert(Long alertID) {
        AlertLog alert = alertLogRepo.findById(alertID).orElse(null);
        if (alert != null) {
            alert.setResolved(true);
            alert.setStatus("RESOLVED");
            alertLogRepo.save(alert);
        }
    }

    public void onStockUpdate(String productID) {
        Product product = inventoryRepo.findById(productID).orElse(null);
        if (product != null && product.getCurrentQuantity() <= THRESHOLD) {
            String message = "Low stock alert for " + product.getName() + ". Only " + product.getCurrentQuantity() + " left.";
            AlertLog alert = new AlertLog(productID, message, "LOW_STOCK");
            alertLogRepo.save(alert);
            notifier.sendLowStockAlert(alert);
        }
    }
}
