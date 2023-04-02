package me.xiaoying.bot.server;

import me.xiaoying.bot.command.Command;
import me.xiaoying.bot.command.PluginCommand;
import me.xiaoying.bot.enums.InfoType;
import me.xiaoying.bot.plugin.*;
import me.xiaoying.bot.utils.InfoUtil;
import me.xiaoying.bot.utils.SystemUtil;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * bot服务
 */
public class BotServer implements Server {
    private final PluginManager pluginManager = new SimplePluginManager(this);
    private final Map<String, PluginCommand> pluginCommand = new HashMap<>();

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
                List<Command> commands = plugin.getDescription().getCommands();
                commands.forEach(command -> {
                    pluginCommand.put(command.getName(), null);
                    command.getAliases().forEach(string -> pluginCommand.put(string, null));
                });

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
        pluginManager.clearPlugins();
        loadPlugins();
        enablePlugins();
    }


    @Override
    public Logger getLogger() {
        return Logger.getLogger("BotServer");
    }

    @Override
    public PluginCommand getPluginCommand(@NotNull String command) {
        return this.pluginCommand.get(command);
    }

    @Override
    public void shutdown() {

    }
}