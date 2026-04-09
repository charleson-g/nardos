package com.nardo.platform.business.control;

import com.nardo.platform.business.entity.Order;
import com.nardo.platform.business.entity.Sale;
import com.nardo.platform.business.entity.StockReport;
import com.nardo.platform.business.entity.StockMovement;
import com.nardo.platform.persistence.StockMovementRepository;
import com.nardo.platform.persistence.OrderRepository;
import com.nardo.platform.persistence.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportController {

    private final StockMovementRepository stockMovementRepo;
    private final OrderRepository orderRepo;
    private final SaleRepository saleRepo;

    @Autowired
    public ReportController(StockMovementRepository stockMovementRepo, OrderRepository orderRepo, SaleRepository saleRepo) {
        this.stockMovementRepo = stockMovementRepo;
        this.orderRepo = orderRepo;
        this.saleRepo = saleRepo;
    }

    public StockReport generateStockReport(LocalDateTime startDate, LocalDateTime endDate, String format) {
        return generateSummary(format, startDate, endDate);
    }

    public StockReport generateSummary(String format, LocalDateTime startDate, LocalDateTime endDate) {
        List<StockMovement> movements = stockMovementRepo.findByTimestampBetween(startDate, endDate);
        int totalItems = 0;
        for (StockMovement m : movements) {
            if ("SALE".equals(m.getMovementType()) || "ONLINE_ORDER".equals(m.getMovementType())) {
                totalItems += Math.abs(m.getQuantityChanged());
            }
        }

        List<Order> matchingOrders = orderRepo.findByOrderTimestampBetween(startDate, endDate);
        double totalRevenue = matchingOrders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();
                
        List<Sale> matchingSales = saleRepo.findByTimestampBetween(startDate, endDate);
        totalRevenue += matchingSales.stream()
                .mapToDouble(Sale::getTotalAmount)
                .sum();

        return new StockReport(format, totalItems, totalRevenue, movements);
    }
}
