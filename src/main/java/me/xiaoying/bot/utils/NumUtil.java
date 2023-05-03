package me.xiaoying.bot.utils;

/**
 * 工具类 数字
 */
public class NumUtil {
    /**
     * 是否为数字
     *
     * @param str 字符串
     * @return 逻辑值
     */
    public static boolean isNum(String str) {
        return Character.isDigit(str.charAt(0));
    }

    public static boolean isLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}