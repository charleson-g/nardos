package com.nardo.platform.business.control;

import com.nardo.platform.business.entity.*;
import com.nardo.platform.persistence.InventoryRepository;
import com.nardo.platform.persistence.OrderRepository;
import com.nardo.platform.persistence.PickupSlotRepository;
import com.nardo.platform.persistence.StockMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderController {

    private final OrderRepository orderRepo;
    private final InventoryRepository inventoryRepo;
    private final PickupSlotRepository pickupSlotRepo;
    private final StockMovementRepository stockMovementRepo;
    private final NotificationController notificationController;
    private final StockMonitorController stockMonitor;

    @Autowired
    public OrderController(OrderRepository orderRepo, InventoryRepository inventoryRepo, PickupSlotRepository pickupSlotRepo, StockMovementRepository stockMovementRepo, NotificationController notificationController, StockMonitorController stockMonitor) {
        this.orderRepo = orderRepo;
        this.inventoryRepo = inventoryRepo;
        this.pickupSlotRepo = pickupSlotRepo;
        this.stockMovementRepo = stockMovementRepo;
        this.notificationController = notificationController;
        this.stockMonitor = stockMonitor;
    }

    public List<Product> getAllProducts() {
        return inventoryRepo.findByIsAvailableTrue();
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public List<Product> viewAvailableItems() {
        return inventoryRepo.findByCurrentQuantityGreaterThanAndIsAvailableTrue(0);
    }

    public List<PickupSlot> getAvailablePickupSlots() {
        java.time.LocalTime now = java.time.LocalTime.now();
        for (int i = 1; i <= 8; i++) {
            int minutes = now.getMinute();
            int remainder = minutes % 15;
            java.time.LocalTime nextSlot = now.plusMinutes(15 - remainder).plusMinutes((i - 1) * 15).withSecond(0).withNano(0);
            
            String slotId = "SLOT-" + String.format("%02d%02d", nextSlot.getHour(), nextSlot.getMinute());
            
            if (!pickupSlotRepo.existsById(slotId)) {
                pickupSlotRepo.save(new PickupSlot(slotId, nextSlot, 5));
            }
        }
        return pickupSlotRepo.findByStartTimeAfterAndIsBookedFalse(now);
    }

    public List<PickupSlot> getAvailableSlots() {
        return getAvailablePickupSlots();
    }

    public boolean initiateOrder(Cart cart) {
        return true;
    }

    @Transactional
    public Order processPayment(Cart cart, double amount, String cardNum, String pickupTimeStr) {
        Order order = new Order(UUID.randomUUID().toString(), LocalDateTime.now(), amount);
        
        for (CartItem ci : cart.getItems()) {
            OrderItem oi = new OrderItem(order, ci.getProduct(), ci.getQuantity(), ci.getProduct().getPrice());
            order.addItem(oi);
            
            Product product = inventoryRepo.findById(ci.getProduct().getProductID()).orElse(null);
            if (product != null) {
                product.setCurrentQuantity(product.getCurrentQuantity() - ci.getQuantity());
                inventoryRepo.save(product);
                StockMovement movement = new StockMovement(product.getProductID(), -ci.getQuantity(), "ONLINE_ORDER", "Customer");
                stockMovementRepo.save(movement);
                stockMonitor.onStockUpdate(product.getProductID());
            }
        }
        
        Payment payment = new Payment(UUID.randomUUID().toString(), "TXN-" + System.currentTimeMillis(), amount);
        order.markAsPaid(payment);
        
        Order savedOrder = orderRepo.save(order);
        notificationController.sendOrderConfirmation(savedOrder);
        return savedOrder;
    }

    @Transactional
    public Order processOrder(Order order, Payment payment, String slotId) {
        PickupSlot slot = pickupSlotRepo.findById(slotId).orElse(null);
        if (slot == null || slot.isBooked()) {
            throw new IllegalStateException("Slot unavailable");
        }

        // Verify stock and update inventory
        for (OrderItem item : order.getItems()) {
            Product product = inventoryRepo.findById(item.getProduct().getProductID())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            
            if (product.getCurrentQuantity() < item.getQuantity()) {
                throw new IllegalStateException("Insufficient stock for product: " + product.getName());
            }
            
            product.setCurrentQuantity(product.getCurrentQuantity() - item.getQuantity());
            inventoryRepo.save(product);
            
            StockMovement movement = new StockMovement(product.getProductID(), -item.getQuantity(), "ONLINE_ORDER", "Customer");
            stockMovementRepo.save(movement);
            
            stockMonitor.onStockUpdate(product.getProductID());
        }

        order.setTotalAmount(payment.getAmountPaid());
        order.setStatus("PAID");
        order.setPayment(payment);
        
        slot.setBooked(true);
        pickupSlotRepo.save(slot);
        
        Order savedOrder = orderRepo.save(order);
        notificationController.sendOrderConfirmation(savedOrder);
        return savedOrder;
    }
    
    public Order trackOrder(String orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }
    
    public String checkOrderStatus(String id) {
        return orderRepo.findById(id).map(Order::getStatus).orElse("NotFound");
    }
    
    public Order getOrder(String id) {
        return orderRepo.findById(id).orElse(null);
    }
    
    public void updateStatus(String orderId, String newStatus) {
        orderRepo.findById(orderId).ifPresent(o -> {
            o.setStatus(newStatus);
            orderRepo.save(o);
        });
    }
}
