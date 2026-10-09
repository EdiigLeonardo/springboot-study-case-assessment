package com.mastery.arrays;

import java.util.Arrays;

public class TwoPointerReversalTest {
    public static void main(String[] args) {
        testEvenLength();
        testOddLength();
        testSingleAndEmpty();
        System.out.println("All TwoPointerReversal tests passed successfully.");
    }

    private static void testEvenLength() {
        int[] arr = {1, 2, 3, 4};
        TwoPointerReversal.reverse(arr);
        assert Arrays.equals(arr, new int[]{4, 3, 2, 1}) : "Failed on even length";
    }

    private static void testOddLength() {
        int[] arr = {10, 20, 30, 40, 50};
        TwoPointerReversal.reverse(arr);
        assert Arrays.equals(arr, new int[]{50, 40, 30, 20, 10}) : "Failed on odd length";
    }

    private static void testSingleAndEmpty() {
        int[] single = {42};
        TwoPointerReversal.reverse(single);
        assert single[0] == 42 : "Failed on single element";

        int[] empty = {};
        TwoPointerReversal.reverse(empty);
        assert empty.length == 0 : "Failed on empty array";
    }
}
