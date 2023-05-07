package me.xiaoying.bot.file;

import me.xiaoying.bot.configuration.YamlConfiguration;
import me.xiaoying.bot.utils.SystemUtil;

import java.io.File;
import java.io.IOException;

/**
 * 文件 permission.yml
 */
public class FilePermission {
    public static YamlConfiguration permission;

    public static void filePermission() {
        File configFile = new File(SystemUtil.getSystemPath(), "permissions.yml");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        permission = YamlConfiguration.loadConfiguration(new File(SystemUtil.getSystemPath(), "permissions.yml"));
    }
}