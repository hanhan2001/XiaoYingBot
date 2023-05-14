package me.xiaoying.bot.file;

import me.xiaoying.bot.configuration.YamlConfiguration;
import me.xiaoying.bot.utils.SystemUtil;

import java.io.File;

/**
 * 文件 Configuration.yml
 */
public class FileConfig {
    public static YamlConfiguration config;

    /**
     * BOT_ACCOUNT          账号
     * BOT_PASSWORD         密码
     * BOT_PROTOCOl         设备协议
     * BOT_ENCRYPT          是否加密
     * BOT_AUTHORIZE        登录方式
     */
    public static Long BOT_ACCOUNT;
    public static String BOT_PASSWORD,
            BOT_PROTOCOl,
            BOT_DEVICES,
            BOT_AUTHORIZE;

    public static String SET_VARIABLE_DATA,
            SET_MESSAGE_LOG_GROUP,
            SET_MESSAGE_LOG_PRIVATE,
            SET_MESSAGE_LOG_TEMP,
            SET_GROUP_LOG_MEMBER_JOIN,
            SET_GROUP_LOG_MEMBER_QUIT;
    public static boolean BOT_ENCRYPT;

    public static void fileConfig() {
        File configFile = new File(SystemUtil.getSystemPath(), "Configuration.yml");
        if (!configFile.exists())
            FileManager.saveResource(SystemUtil.getSystemPath(), "Configuration.yml");

        config = YamlConfiguration.loadConfiguration(new File(SystemUtil.getSystemPath(), "Configuration.yml"));

        readConfig();
    }

    private static void readConfig() {
        BOT_ACCOUNT = config.getLong("Bot.Account");
        BOT_DEVICES = config.getString("Bot.Devices");
        BOT_PASSWORD = config.getString("Bot.Password");
        BOT_PROTOCOl = config.getString("Bot.Protocol");
        BOT_AUTHORIZE = config.getString("Bot.AuthorizeType");

        SET_VARIABLE_DATA = config.getString("Set.DateFormat");
        SET_MESSAGE_LOG_TEMP = config.getString("Set.LogFormat.Message.Temp");
        SET_MESSAGE_LOG_GROUP = config.getString("Set.LogFormat.Message.Group");
        SET_MESSAGE_LOG_PRIVATE = config.getString("Set.LogFormat.Message.Private");

        SET_GROUP_LOG_MEMBER_JOIN = config.getString("Set.LogFormat.Group.MemberJoin");
        SET_GROUP_LOG_MEMBER_QUIT = config.getString("Set.LogFormat.Group.MemberQuit");

        BOT_ENCRYPT = config.getBoolean("Bot.Encrypt");
    }
}