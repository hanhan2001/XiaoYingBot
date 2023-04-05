package me.xiaoying.bot.command;

import java.util.HashMap;
import java.util.Map;

/**
 * 命令
 */
public class PluginCommand {
    private Map<String, Command> commandMap = new HashMap<>();

    public Command getCommand(String name) {
        return this.commandMap.get(name);
    }

    public void register(String name, Command command) {
        commandMap.put(name, command);
    }

    public void unregister(String name) {
        if (this.commandMap.get(name) == null)
            return;

        this.commandMap.remove(name);
    }

    public void unregisterAll() {
        this.commandMap.clear();
    }
}