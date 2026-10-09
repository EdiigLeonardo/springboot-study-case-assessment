package com.mastery.search;

import java.util.Comparator;
import java.util.List;

public class CustomBinarySearchTest {
    public record Product(int id, String name, double price) {}

    public static void main(String[] args) {
        List<Product> products = List.of(
                new Product(1, "Keyboard", 45.0),
                new Product(2, "Mouse", 25.0),
                new Product(3, "Monitor", 180.0),
                new Product(4, "Laptop", 850.0)
        ).stream().sorted(Comparator.comparingDouble(Product::price)).toList();

        Comparator<Product> priceComparator = Comparator.comparingDouble(Product::price);

        int idx = CustomBinarySearch.search(products, new Product(0, "Target", 45.0), priceComparator);
        assert idx >= 0 && products.get(idx).name().equals("Keyboard") : "Search should locate Keyboard";

        int missingIdx = CustomBinarySearch.search(products, new Product(0, "Missing", 99.0), priceComparator);
        assert missingIdx < 0 : "Should return negative insertion point for missing element";

        System.out.println("CustomBinarySearch tests passed successfully.");
    }
}
