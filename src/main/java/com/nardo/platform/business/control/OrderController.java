package com.nardo.platform.business.control;

import com.nardo.platform.business.entity.Order;
import com.nardo.platform.business.entity.Payment;
import com.nardo.platform.business.entity.Product;
import com.nardo.platform.persistence.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

/**
 * Control class for the "Place Online Order" and "Complete Online Payment" use cases.
 * Serves as the central coordinator for the ordering workflow.
 */

@Service


public class OrderController {

    @Autowired
    private InventoryRepository inventoryRepo;

    /**
     * Starts the order process and places a 5-minute "Soft Lock"
     * This prevents "no-show" inventory losses while the customer is paying.
     */

    public boolean initiateOrder(String productID, int qty) {
        Product product = inventoryRepo.findById(productID).orElse(null);

        if (product !=null && product.applySoftLock(qty)){
            inventoryRepo.save(product); // Persistence of the temporary lock
            return true;
        }
        return false; // Stock unavailable or product not found
    }
    /**
     * Orchestrates the "Sure Money" payment handshake.
     * Only commits inventory deduction AFTER successful gateway confirmation.
     */
    public void processPaymentSuccess(String orderID, String productID, int qty, double amount, String gatewayID){
        // 1. Create the persistent Payment Record
        Payment pmt = new Payment(orderID + "-PMT", gatewayID, amount);

        // 2. Fetch and update the Product
        Product product = inventoryRepo.findById(productID).get();
        product.deductStock(qty); // Convert Soft Lock to permanent deduction

        // 3. Update the order state to "PAID"
        Order order = new Order(orderID, LocalDateTime.now().plusMinutes(15), amount);
        order.markAsPaid(pmt);

        // 4. Save all changes to the Cloud DB
        inventoryRepo.save(product);
        // Note: An OrderRepository and PaymentRepository would also be here
        System.out.println("Order " + orderID + " secured. Inventory updated.");
    }

    /*
    Releases stock if the payment is canceled or the 5-minute timer expires.
     */
    public void handlePaymentFailure(String productID, int qty){
        Product product = inventoryRepo.findById(productID).get();
        product.releaseLock(qty);
        inventoryRepo.save(product);
    }
}
