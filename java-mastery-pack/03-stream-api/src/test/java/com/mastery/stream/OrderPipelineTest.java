package com.mastery.stream;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OrderPipelineTest {
    public static void main(String[] args) {
        List<OrderPipeline.Order> orders = List.of(
                new OrderPipeline.Order("1", "CUST_1", new BigDecimal("100.50"), OrderPipeline.Order.Status.COMPLETED),
                new OrderPipeline.Order("2", "CUST_1", new BigDecimal("49.50"), OrderPipeline.Order.Status.COMPLETED),
                new OrderPipeline.Order("3", "CUST_1", new BigDecimal("500.00"), OrderPipeline.Order.Status.CANCELLED),
                new OrderPipeline.Order("4", "CUST_2", new BigDecimal("200.00"), OrderPipeline.Order.Status.COMPLETED),
                new OrderPipeline.Order("5", "CUST_2", new BigDecimal("50.00"), OrderPipeline.Order.Status.PENDING)
        );

        Map<String, OrderPipeline.CustomerOrderSummary> summary = OrderPipeline.aggregateCompletedOrders(orders);

        assert summary.size() == 2 : "Expected 2 customers in summary";
        assert summary.get("CUST_1").orderCount() == 2 : "CUST_1 should have 2 completed orders";
        assert summary.get("CUST_1").totalSpent().compareTo(new BigDecimal("150.00")) == 0 : "CUST_1 total spent incorrect";
        assert summary.get("CUST_2").orderCount() == 1 : "CUST_2 should have 1 completed order";
        assert summary.get("CUST_2").totalSpent().compareTo(new BigDecimal("200.00")) == 0 : "CUST_2 total spent incorrect";

        System.out.println("OrderPipeline tests passed successfully.");
    }
}
