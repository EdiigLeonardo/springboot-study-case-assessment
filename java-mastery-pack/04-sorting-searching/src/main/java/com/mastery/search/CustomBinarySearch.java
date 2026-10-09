package com.mastery.search;

import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;

public final class CustomBinarySearch {
    private CustomBinarySearch() {}

    /**
     * Performs binary search over an ordered List using a custom Comparator.
     * Enforces RandomAccess contract to guarantee O(log N) runtime.
     */
    public static <T> int search(List<? extends T> list, T key, Comparator<? super T> comparator) {
        if (!(list instanceof RandomAccess)) {
            throw new IllegalArgumentException("List must implement java.util.RandomAccess for O(log N) binary search.");
        }

        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            T midVal = list.get(mid);
            int cmp = comparator.compare(midVal, key);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -(low + 1);
    }
}
