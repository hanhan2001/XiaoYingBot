package me.xiaoying.bot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类 日期
 */
public class DateUtil {
    // 设置日期格式
    public static String date(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }
}
