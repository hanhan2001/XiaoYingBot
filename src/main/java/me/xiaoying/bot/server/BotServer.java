package me.xiaoying.bot.server;

import me.xiaoying.bot.enums.InfoType;
import me.xiaoying.bot.event.HandlerList;
import me.xiaoying.bot.plugin.*;
import me.xiaoying.bot.utils.InfoUtil;
import me.xiaoying.bot.utils.ServerUtil;
import me.xiaoying.bot.utils.SystemUtil;

import java.io.File;
import java.util.logging.Logger;

/**
 * bot服务
 */
public class BotServer implements Server {
    private final PluginManager pluginManager = new SimplePluginManager(this);

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
            InfoUtil.sendMessage(InfoType.WARING, ex.getMessage() + " loading " + plugin.getDescription().getName() + " (Is it up to date?)");
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
                InfoUtil.sendMessage(InfoType.WARING, ex.getMessage() + " initializing " + plugin.getDescription().getName() + " (Is it up to date?)");
                ex.printStackTrace();
            }
        }
    }

    @Override
    public PluginManager getPluginManager() {
        return this.pluginManager;
    }

    @Override
    public void reload() {
        Plugin[] plugins = pluginManager.getPlugins();
        for (Plugin plugin : plugins) {
            HandlerList.unregisterAll(plugin);
            InfoUtil.sendMessage("Disabled plugin " + plugin.getName() + " " + plugin.getDescription().getVersion() + " by " + plugin.getDescription().getAuthors());
            plugin.onDisable();
        }
        pluginManager.clearPlugins();
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