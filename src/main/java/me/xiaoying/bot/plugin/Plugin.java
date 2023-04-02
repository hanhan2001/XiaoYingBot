package me.xiaoying.bot.plugin;

import me.xiaoying.bot.command.PluginCommand;
import me.xiaoying.bot.server.Server;

import java.io.File;

public interface Plugin {
    File getDataFolder();

    PluginDescriptionFile getDescription();

    void saveConfig();

    void saveDefaultConfig();

    void saveResource(String var1, boolean var2);

    Server getServer();

    void reloadConfig();

    PluginLoader getPluginLoader();

    PluginCommand getPluginCommand(String command);

    boolean isEnabled();

    void onEnable();
    void onDisable();
    void onLoad();


    boolean isNaggable();

    void setNaggable(boolean var1);

    String getName();
}