package org.gtlcore.gtlcore.utils;

import java.util.ArrayList;
import java.util.List;

public class StructureSlicer {

    /**
     * 对输入的二维数组进行切片和插入操作
     *
     * @param input 输入的二维字符串数组
     * @param n     前切片取前n个元素（索引0到n-1，包含n-1）
     * @param m     后切片从第m个元素开始（索引m-1到末尾，包含m-1）
     * @param q     额外字符串取第q个元素（索引q-1）
     * @param k     插入数量的起始值
     * @param l     插入数量的结束值
     * @return 包含所有生成结果的列表，每个元素对应一个i值的完整String[][]
     */
    public static List<String[][]> sliceAndInsert(String[][] input, int n, int m, int q, int k, int l) {
        if (input == null || input.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty");
        }

        List<String[][]> results = new ArrayList<>();

        for (int i = k; i <= l; i++) {
            String[][] result = processForInsertCount(input, n, m, q, i);
            results.add(result);
        }

        return results;
    }

    private static String[][] processForInsertCount(String[][] input, int n, int m, int q, int insertCount) {
        List<String[]> processedRows = new ArrayList<>();

        for (String[] row : input) {
            if (row == null || row.length == 0) {
                processedRows.add(new String[0]);
                continue;
            }

            String[] frontSlice = sliceArray(row, 0, n);
            String[] backSlice = sliceArray(row, m - 1, row.length);
            String extraString = (q > 0 && q <= row.length) ? row[q - 1] : "";
            String[] combinedRow = combineWithInserts(frontSlice, backSlice, extraString, insertCount);

            processedRows.add(combinedRow);
        }

        return processedRows.toArray(new String[0][]);
    }

    private static String[] sliceArray(String[] array, int start, int end) {
        if (start < 0) start = 0;
        if (end > array.length) end = array.length;
        if (start >= end) return new String[0];

        String[] slice = new String[end - start];
        System.arraycopy(array, start, slice, 0, end - start);
        return slice;
    }

    private static String[] combineWithInserts(String[] front, String[] back, String extra, int insertCount) {
        int totalLength = front.length + insertCount + back.length;
        String[] combined = new String[totalLength];

        int index = 0;

        for (String s : front) {
            combined[index++] = s;
        }

        for (int i = 0; i < insertCount; i++) {
            combined[index++] = extra;
        }

        for (String s : back) {
            combined[index++] = s;
        }

        return combined;
    }
}
