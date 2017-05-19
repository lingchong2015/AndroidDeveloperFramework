package com.curry.stephen.lcandroidlib.utils;

import java.util.List;

/**
 * Created by Administrator on 2017/2/19.
 */

public class SortAlgorithmHelper {

    public static void sortString(List<String> sList, boolean isAsc) {
        String s1;
        String s2;
        for (int i = 1; i < sList.size(); ++i) {
            for (int j = i; j > 0; --j) {
                s1 = sList.get(j - 1);
                s2 = sList.get(j);
                if (isAsc && (compare(s1, s2) == 1))  {
                    sList.set(j - 1, s2);
                    sList.set(j, s1);
                } else if(!isAsc && (compare(s1, s2) == -1)) {
                    sList.set(j - 1, s2);
                    sList.set(j, s1);
                }
            }
        }
    }

    /**
     * s1 < s2 => -1, s1 = s2 => 0, s1 > s2 => 1;
     */
    public static int compare(String s1, String s2) {
        String a = s1.toUpperCase();
        String b = s2.toUpperCase();
        int minLength = a.length();
        if (minLength > b.length()) {
            minLength = b.length();
        }

        char[] aArrays = a.toCharArray();
        char[] bArrays = b.toCharArray();
        for (int i = 0; i < minLength; ++i) {
            if (aArrays[i] > bArrays[i]) {
                return 1;
            } else if (aArrays[i] < bArrays[i]) {
                return -1;
            }
        }

        if (a.length() > b.length()) {
            return 1;
        } else if (a.length() == b.length()) {
            return 0;
        } else {
            return -1;
        }
    }
}
