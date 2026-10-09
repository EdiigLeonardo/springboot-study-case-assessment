package com.mastery.arrays;

public final class TwoPointerReversal {
    private TwoPointerReversal() {}

    /**
     * In-place array reversal using the two-pointer technique.
     * Space Complexity: O(1) auxiliary memory.
     * Time Complexity: O(N) where N is array length.
     */
    public static void reverse(int[] array) {
        if (array == null || array.length <= 1) {
            return;
        }
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
    }
}
