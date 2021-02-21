package com.example.testact.http;

/**
 * 正则工具类
 */
public class Number {

    /**
     * Double 格式
     *
     * @param number
     * @return
     */
    public static double formatDouble(String number) {
        if (number == null || number.equals("null") || number.length() == 0) {
            return 0.00d;
        }
        if (!number.contains(".")) {
            number = number + ".00";
        }
        return Double.parseDouble(number);
    }

    /**
     * Integer 格式
     *
     * @param number
     * @return
     */
    public static int formatInt(String number) {
        if (number == null || number.equals("null") || number.length() == 0) {
            return 0;
        }
        if (number.contains(".")) {
            number = number.substring(0, number.lastIndexOf("."));
        }
        return Integer.parseInt(number);
    }

    /**
     * Long 格式
     *
     * @param number
     * @return
     */
    public static long formatLong(String number) {
        if (number == null || number.equals("null") || number.length() == 0) {
            return 0;
        }
        return Long.parseLong(number);
    }

    /**
     * Float 格式
     *
     * @param number
     * @return
     */
    public static float formatFloat(String number) {
        if (number == null || number.equals("null") || number.length() == 0) {
            return 0.00f;
        }
        if (!number.contains(".")) {
            number = number + ".00";
        }
        return Float.parseFloat(number);
    }

    /**
     * Int 格式
     *
     * @param number
     * @return
     */
    public static String formatIntStr(String number) {
        if (number == null || number.equals("null") || number.length() == 0) {
            return "0";
        }
        return number;
    }

    /**
     * Long 格式
     *
     * @param number
     * @return
     */
    public static String formatLongStr(String number) {
        if (number == null || number.equals("null") || number.length() == 0) {
            return "0";
        }
        return number;
    }

    /**
     * Double 格式
     *
     * @param number
     * @return
     */
    public static String formatDoubleStr(String number) {
        if (number == null || number.equals("null") || number.length() == 0) {
            return "0.00";
        }
        return number;
    }

    /**
     * Float 格式
     *
     * @param number
     * @return
     */
    public static String formatFloatStr(String number) {
        if (number == null || number.equals("null") || number.length() == 0) {
            return "0.00";
        }
        return number;
    }


}
