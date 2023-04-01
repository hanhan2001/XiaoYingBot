package me.xiaoying.bot.server;

import me.xiaoying.bot.plugin.Plugin;
import me.xiaoying.bot.plugin.PluginManager;

import java.util.Map;
import java.util.logging.Logger;

public interface Server {
    PluginManager getPluginManager();

    void reload();

    Logger getLogger();

    void shutdown();
}