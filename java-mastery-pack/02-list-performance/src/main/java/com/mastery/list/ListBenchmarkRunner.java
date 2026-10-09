package com.mastery.list;

import java.util.*;

public class ListBenchmarkRunner {
    private static final int ITERATIONS = 100_000;

    public static void main(String[] args) {
        System.out.println("=== 100k Element Benchmark: ArrayList vs LinkedList ===");

        // Test 1: Sequential append
        long start = System.nanoTime();
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < ITERATIONS; i++) arrayList.add(i);
        long alAppend = System.nanoTime() - start;

        start = System.nanoTime();
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < ITERATIONS; i++) linkedList.add(i);
        long llAppend = System.nanoTime() - start;

        System.out.printf("Append -> ArrayList: %d ms | LinkedList: %d ms%n",
                alAppend / 1_000_000, llAppend / 1_000_000);

        // Test 2: Head deletion remove(0)
        start = System.nanoTime();
        for (int i = 0; i < 10_000; i++) arrayList.remove(0);
        long alRemoveHead = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < 10_000; i++) linkedList.remove(0);
        long llRemoveHead = System.nanoTime() - start;

        System.out.printf("remove(0) [10k ops] -> ArrayList: %d ms | LinkedList: %d ms%n",
                alRemoveHead / 1_000_000, llRemoveHead / 1_000_000);

        // Test 3: Random access get(index)
        start = System.nanoTime();
        long alSum = 0;
        for (int i = 0; i < 10_000; i++) alSum += arrayList.get(i);
        long alAccess = System.nanoTime() - start;

        start = System.nanoTime();
        long llSum = 0;
        for (int i = 0; i < 10_000; i++) llSum += linkedList.get(i);
        long llAccess = System.nanoTime() - start;

        System.out.printf("get(i) [10k ops] -> ArrayList: %d ms | LinkedList: %d ms%n",
                alAccess / 1_000_000, llAccess / 1_000_000);
    }
}
