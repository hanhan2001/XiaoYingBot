package me.xiaoying.bot.utils;

/**
 * 工具类 Long
 */
public class LongUtil {
    /**
     * 是否为Long
     *
     * @param string 内容
     * @return 逻辑值
     */
    public static boolean isLong(String string) {
        try {
            Long.parseLong(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}