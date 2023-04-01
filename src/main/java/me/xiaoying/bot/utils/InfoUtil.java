package me.xiaoying.bot.utils;

import me.xiaoying.bot.enums.InfoType;

/**
 * 工具类 日志
 */
public class InfoUtil {
    /**
     * 发送日志
     *
     * @param message 内容
     */
    public static void sendMessage(String message) {
        sendMessage(InfoType.INFO, message);
    }

    /**
     * 发送日志
     *
     * @param infoType 类型
     * @param message 内容
     */
    public static void sendMessage(InfoType infoType, String message) {
        switch (infoType) {
            case INFO:
                System.out.println("[INFO] " + message);
                break;
            case DEBUG:
                System.out.println("[DEBUG] " + message);
                break;
            case WARING:
                System.out.println("[WARING] " + message);
                break;
            case ERROR:
                System.out.println("[ERROR] " + message);
                break;
        }
    }
}