package com.mastery.list;

import java.util.*;

public class RandomAccessInspector {
    public static void main(String[] args) {
        List<String> arrayList = new ArrayList<>();
        List<String> linkedList = new LinkedList<>();

        System.out.println("ArrayList implements RandomAccess: " + (arrayList instanceof RandomAccess));
        System.out.println("LinkedList implements RandomAccess: " + (linkedList instanceof RandomAccess));
    }
}
