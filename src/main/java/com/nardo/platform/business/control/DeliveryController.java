package com.nardo.platform.business.control;

import com.nardo.platform.business.entity.*;
import com.nardo.platform.persistence.InventoryRepository;
import com.nardo.platform.persistence.StockMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeliveryController {

    private final InventoryRepository inventoryRepo;
    private final StockMovementRepository stockMovementRepo;
    private final StockMonitorController stockMonitor;

    @Autowired
    public DeliveryController(InventoryRepository inventoryRepo, StockMovementRepository stockMovementRepo, StockMonitorController stockMonitor) {
        this.inventoryRepo = inventoryRepo;
        this.stockMovementRepo = stockMovementRepo;
        this.stockMonitor = stockMonitor;
    }

    @Transactional
    public boolean processDelivery(String productID, int quantity, String supplier) {
        Product product = inventoryRepo.findById(productID).orElse(null);
        if (product == null) {
            return false;
        }

        product.setCurrentQuantity(product.getCurrentQuantity() + quantity);
        inventoryRepo.save(product);

        StockMovement movement = new StockMovement(
                productID,
                quantity,
                "DELIVERY",
                "Admin",
                supplier
        );
        stockMovementRepo.save(movement);

        stockMonitor.onStockUpdate(productID);

        return true;
    }
}
