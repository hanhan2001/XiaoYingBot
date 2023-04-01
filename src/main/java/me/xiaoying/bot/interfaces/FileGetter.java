package me.xiaoying.bot.interfaces;

import java.util.List;

/**
 * Yaml 接口类
 */
public interface FileGetter {
    String getString(String key);
    List<String> getStringList(String key);
    int getInt(String key);
    boolean getBoolean(String key);
    double getDouble(String key);
    long getLong(String key);
}