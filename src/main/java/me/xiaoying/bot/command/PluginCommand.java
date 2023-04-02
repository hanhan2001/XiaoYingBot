package me.xiaoying.bot.command;

import me.xiaoying.bot.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginCommand extends Command {
    public static Map<PluginCommand, List<Command>> pluginCommandList = new HashMap<>();
    private Plugin owningPlugin;
    private CommandExecutor executor;

    public PluginCommand(String name) {
        super(name);
    }

    protected PluginCommand(CommandExecutor executor, Plugin plugin) {
        this.executor = executor;
        this.owningPlugin = plugin;
    }

    public Plugin getPlugin() {
        return this.owningPlugin;
    }

    public CommandExecutor getExecutor() {
        return this.executor;
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }
}