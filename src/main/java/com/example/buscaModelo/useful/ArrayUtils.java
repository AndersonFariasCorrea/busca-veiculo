package com.example.buscaModelo.useful;

import java.util.Arrays;
public class ArrayUtils {
    public static boolean in_array(int num, int[] arr) {
        return Arrays.stream(arr).anyMatch(element -> element == num);
    }
    public static boolean indexExists(Object[] arr, int index) {
        return index >= 0 && index < arr.length;
    }
}
