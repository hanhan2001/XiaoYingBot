package me.xiaoying.bot.interfaces;

import java.util.List;

/**
 * Mysql 接口类 处理基本数据库
 */
public interface MysqlGetter {
    boolean isEmpty() throws Exception;
    int getTableLineSize() throws Exception;

    /**
     * 获取字符串
     *
     * @param path WHERE 判断 对象
     * @param key WHERE 判断 值
     * @param getKey 获取内容
     * @return 字符串
     * @throws Exception 抛出报错
     */
    String getString(String path, String key, String getKey) throws Exception;

    /**
     * 获取字符串
     *
     * @param path WHERE 判断 对象
     * @param key WHERE 判断 值
     * @param getKey 获取内容
     * @param get 查看表显示列
     * @return 字符串
     * @throws Exception 抛出报错
     */
    String getString(String path, String key, String getKey, String... get) throws Exception;
    int getInt(String path, String key, String getKey) throws Exception;
    int getInt(String path, String key, String getKey, String... get) throws Exception;
    boolean getBoolean(String path, String key, String getKey) throws Exception;
    boolean getBoolean(String path, String key, String getKey, String... get) throws Exception;
    List<String> getStringList(String path, String key, String getKey) throws Exception;
    List<String> getStringList(String path, String key, String getKey, String... get) throws Exception;
    boolean isRecordExists(String path, String key) throws Exception;


    void insertString(String... str) throws Exception;
    void setString(String path, String vault) throws Exception;
    void setString(String path, String vault, String conditionPath, String conditionVault) throws Exception;

    void delString(String path, String key) throws Exception;
}