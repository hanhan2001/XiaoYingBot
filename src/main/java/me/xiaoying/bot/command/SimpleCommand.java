package me.xiaoying.bot.command;

import me.xiaoying.bot.plugin.IllegalCommandException;
import me.xiaoying.bot.plugin.Plugin;

import java.util.*;

public class SimpleCommand {
    private final Map<String, List<PluginCommand>> commandMap = new HashMap<>();

    /**
     * 注册命令
     *
     * @param command 命令
     * @param plugin 来自插件
     */
    public void register(Command command, Plugin plugin) {
        this.register(command, null, plugin);
    }

    /**
     * 注册命令
     *
     * @param command 命令
     * @param executor 处理
     * @param plugin 来自插件
     */
    public void register(Command command, CommandExecutor executor, Plugin plugin) {
        List<PluginCommand> list;
        if (this.commandMap.get(plugin.getName()) != null)
            list = this.commandMap.get(plugin.getName());
        else
            list = new ArrayList<>();

        for (PluginCommand pluginCommand : list) {
            if (pluginCommand.getCommand().getName().equalsIgnoreCase(command.getName())) {
                try {
                    throw new IllegalCommandException("command already registered");
                } catch (IllegalCommandException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        list.add(new PluginCommand(command, executor));
        this.commandMap.put(plugin.getName(), list);
    }

    public void unregister(Plugin plugin) {
        if (this.commandMap.get(plugin.getName()) != null)
            return;

        this.commandMap.remove(plugin.getName());
    }

    public void unregister(Plugin plugin, String name) {
        if (this.commandMap.get(plugin.getName()) == null)
            return;

        Collection<PluginCommand> list = this.commandMap.get(plugin.getName());
        list.forEach(pluginCommand -> {
            if (pluginCommand.getCommand().getName().equalsIgnoreCase(name))
                list.remove(pluginCommand);
        });
    }

    public void unregisters() {
        this.commandMap.clear();
    }

    public PluginCommand getCommand(String name) {
        PluginCommand command = null;

        for (List<PluginCommand> value : this.commandMap.values()) {
            for (PluginCommand command1 : value) {
                if (!command1.getCommand().getName().equalsIgnoreCase(name))
                    continue;

                command = command1;
                break;
            }
        }
        return command;
    }

    public PluginCommand getCommand(String name, Plugin plugin) {
        PluginCommand command = null;

        if (this.commandMap.get(plugin.getName()) == null)
            return null;

        for (PluginCommand command1 : this.commandMap.get(plugin.getName())) {
            if (!command1.getCommand().getName().equalsIgnoreCase(name))
                continue;

            command = command1;
            break;
        }
        return command;
    }

    public List<PluginCommand> getCommands(Plugin plugin) {
        if (this.commandMap.get(plugin.getName()) == null)
            return null;

        return this.commandMap.get(plugin.getName());
    }
}