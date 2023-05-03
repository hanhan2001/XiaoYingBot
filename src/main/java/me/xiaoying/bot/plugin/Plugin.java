package me.xiaoying.bot.plugin;

import me.xiaoying.bot.command.Command;
import me.xiaoying.bot.command.CommandExecutor;
import me.xiaoying.bot.command.PluginCommand;
import me.xiaoying.bot.server.Server;

import java.io.File;
import java.util.List;

public interface Plugin {
    File getDataFolder();

    PluginDescriptionFile getDescription();

    void saveConfig();

    void saveDefaultConfig();

    void saveResource(String var1, boolean var2);

    Server getServer();

    void reloadConfig();

    PluginLoader getPluginLoader();

    boolean isEnabled();

    void onEnable();
    void onDisable();
    void onLoad();

    PluginCommand getCommand(String name);
    void registerCommand(String command);
    void registerCommand(String command, CommandExecutor executor);
    void registerCommand(String command, List<String> alias);
    void registerCommand(String command, List<String> alias, CommandExecutor executor);

    boolean isNaggable();

    void setNaggable(boolean var1);

    String getName();
}