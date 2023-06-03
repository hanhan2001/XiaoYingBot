package me.xiaoying.bot.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * 工具类 JSON
 */
public class JSONUtil {
    /**
     * 判断字符串是否为JSON
     *
     * @param str 字符串
     * @return 逻辑值
     */
    public static boolean isJSON(String str) {
        try {
            JSONObject.parseObject(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}