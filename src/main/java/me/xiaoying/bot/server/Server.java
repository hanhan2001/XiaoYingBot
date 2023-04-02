package me.xiaoying.bot.server;

import me.xiaoying.bot.command.PluginCommand;
import me.xiaoying.bot.plugin.Plugin;
import me.xiaoying.bot.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.logging.Logger;

public interface Server {
    PluginManager getPluginManager();

    void reload();

    Logger getLogger();

    PluginCommand getPluginCommand(@NotNull String command);

    void shutdown();
}