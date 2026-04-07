package com.nardo.platform.business.control;

import com.nardo.platform.business.entity.*;
import com.nardo.platform.persistence.InventoryRepository;
import com.nardo.platform.persistence.SaleRepository;
import com.nardo.platform.persistence.StockMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class SalesController {

    private final InventoryRepository inventoryRepo;
    private final SaleRepository saleRepo;
    private final StockMovementRepository stockMovementRepo;
    private final StockMonitorController stockMonitor;

    @Autowired
    public SalesController(InventoryRepository inventoryRepo, SaleRepository saleRepo, StockMovementRepository stockMovementRepo, StockMonitorController stockMonitor) {
        this.inventoryRepo = inventoryRepo;
        this.saleRepo = saleRepo;
        this.stockMovementRepo = stockMovementRepo;
        this.stockMonitor = stockMonitor;
    }

    @Transactional
    public boolean recordLocalSale(String productID, int quantity) {
        Product product = inventoryRepo.findById(productID).orElse(null);

        if (product != null && product.getCurrentQuantity() >= quantity) {
            double totalAmount = product.getPrice() * quantity;
            Sale sale = new Sale(UUID.randomUUID().toString(), productID, quantity, totalAmount);
            saleRepo.save(sale);

            StockMovement movement = new StockMovement(productID, -quantity, "SALE", "Admin");
            stockMovementRepo.save(movement);

            product.setCurrentQuantity(product.getCurrentQuantity() - quantity);
            inventoryRepo.save(product);

            stockMonitor.onStockUpdate(productID);
            return true;
        }
        return false;
    }
}
