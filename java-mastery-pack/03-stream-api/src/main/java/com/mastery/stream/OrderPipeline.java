package com.mastery.stream;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class OrderPipeline {

    public record Order(String orderId, String customerId, BigDecimal totalAmount, Status status) {
        public enum Status { PENDING, COMPLETED, CANCELLED }
    }

    public record CustomerOrderSummary(String customerId, BigDecimal totalSpent, long orderCount) {}

    /**
     * Filters for COMPLETED orders, groups by customerId, and computes metrics.
     */
    public static Map<String, CustomerOrderSummary> aggregateCompletedOrders(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.status() == Order.Status.COMPLETED)
                .collect(Collectors.groupingBy(
                        Order::customerId,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    BigDecimal total = list.stream()
                                            .map(Order::totalAmount)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                                    return new CustomerOrderSummary(list.get(0).customerId(), total, list.size());
                                }
                        )
                ));
    }
}
