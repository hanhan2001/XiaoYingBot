package me.xiaoying.bot.server;

import me.xiaoying.bot.command.SimpleCommand;
import me.xiaoying.bot.permission.PermissionService;
import me.xiaoying.bot.permission.SimplePermissionService;
import me.xiaoying.bot.plugin.*;
import me.xiaoying.bot.utils.InfoUtil;
import me.xiaoying.bot.utils.SystemUtil;

import java.io.File;
import java.util.logging.Logger;

/**
 * bot服务
 */
public class BotServer implements Server {
    private final PluginManager pluginManager = new SimplePluginManager(this);
    private final SimpleCommand simpleCommand = new SimpleCommand();
    private PermissionService permissionService = new SimplePermissionService();

    public void enablePlugins() {
        Plugin[] plugins = this.pluginManager.getPlugins();

        for (Plugin plugin : plugins) {
            if (plugin.isEnabled())
                continue;

            loadPlugin(plugin);
        }
    }

    private void loadPlugin(Plugin plugin) {
        try {
            this.pluginManager.enablePlugin(plugin);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public void loadPlugins() {
        this.pluginManager.registerInterface(JavaPluginLoader.class);

        File pluginFolder = new File(SystemUtil.getSystemPath() + "/plugins");

        // 不存在文件夹则生成，并退出加载
        if (!pluginFolder.exists()) {
            pluginFolder.mkdir();
            return;
        }

        Plugin[] plugins = this.pluginManager.loadPlugins(pluginFolder);
        for (Plugin plugin : plugins) {
            try {
                String message = String.format("Loading %s %s by %s", plugin.getDescription().getName(), plugin.getDescription().getVersion(), plugin.getDescription().getAuthors());
                InfoUtil.sendMessage(message);

                plugin.onLoad();
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    @Override
    public SimpleCommand getPluginCommand() {
        return this.simpleCommand;
    }

    @Override
    public PermissionService getPermissionService() {
        return permissionService;
    }

    @Override
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public void reload() {
        this.pluginManager.clearPlugins();
        this.simpleCommand.unregisters();
        loadPlugins();
        enablePlugins();
    }

    @Override
    public Logger getLogger() {
        return Logger.getLogger("BotServer");
    }

    @Override
    public void shutdown() {

    }
}