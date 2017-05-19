package com.curry.stephen.lcandroidlib.utils;

/**
 * Created by Administrator on 2017/1/25.
 */

public class ClassSecurityOperationHelper {

    public final static int convertToInt(Object value, int defaultValue) {
        if (value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }

        try {
            return Integer.valueOf(value.toString());
        }catch (Exception ex1) {
            try {
                return Double.valueOf(value.toString()).intValue();
            } catch (Exception ex2) {
                return defaultValue;
            }
        }
    }

    public final static long convertToLong(Object value, long defaultValue) {
        if (value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }

        try {
            return Long.valueOf(value.toString());
        }catch (Exception ex1) {
            try {
                return Double.valueOf(value.toString()).intValue();
            } catch (Exception ex2) {
                return defaultValue;
            }
        }
    }

    public final static double convertToDouble(Object value, double defaultValue) {
        if (value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }

        try {
            return Double.valueOf(value.toString());
        }catch (Exception ex1) {
            return defaultValue;
        }
    }

    public final static float convertToFloat(Object value, float defaultValue) {
        if (value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }

        try {
            return Float.valueOf(value.toString());
        }catch (Exception ex1) {
            return defaultValue;
        }
    }

    public final static String convertToString(Object value, String defaultValue) {
        if (value == null) {
            return defaultValue;
        }

        try {
            return value.toString();
        }catch (Exception ex1) {
            return defaultValue;
        }
    }

    public final static String substring(String s, int start, int end) {
        if (s != null && start >= 0 && end >= start && s.length() > start && s.length() >= end) {
            return s.substring(start, end);
        } else {
            return null;
        }
    }
}
