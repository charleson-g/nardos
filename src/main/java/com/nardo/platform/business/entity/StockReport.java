package com.nardo.platform.business.entity;

import java.util.List;


public class StockReport {
    private final String reportType;
    private final int totalItemsSold;
    private final double totalRevenue;
    private final List<StockMovement> movements;

    public StockReport(String type, int items, double revenue, List<StockMovement> movements) {
        this.reportType = type;
        this.totalItemsSold = items;
        this.totalRevenue = revenue;
        this.movements = movements;
    }

    // Getters for Thymeleaf to display in the HTML table
    public int getTotalItemsSold() { return totalItemsSold; }
    public double getTotalRevenue() { return totalRevenue; }
    public List<StockMovement> getMovements() { return movements; }
    public String getReportType() { return reportType; }
}
