package me.xiaoying.bot.server;

import me.xiaoying.bot.command.SimpleCommand;
import me.xiaoying.bot.permission.PermissionService;
import me.xiaoying.bot.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public interface Server {
    PluginManager getPluginManager();

    SimpleCommand getPluginCommand();

    PermissionService getPermissionService();
    void setPermissionService(PermissionService permissionService);

    void reload();

    Logger getLogger();

    void shutdown();
}