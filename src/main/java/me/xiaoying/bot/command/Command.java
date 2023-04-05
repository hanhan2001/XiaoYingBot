package me.xiaoying.bot.command;

import me.xiaoying.bot.plugin.Plugin;

import java.util.List;

/**
 * 命令 命令
 */
public class Command {
    String name;
    List<String> alias;
    CommandExecutor executor;

    public Command(String name, CommandExecutor executor) {
        this.name = name;
        this.alias = null;
        this.executor = executor;
    }

    public Command(String name, List<String> alias, CommandExecutor executor) {
        this.name = name;
        this.alias = alias;
        this.executor = executor;
    }

    public String getName() {
        return name;
    }

    public List<String> getAlias() {
        return alias;
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    public CommandExecutor getExecutor() {
        return this.executor;
    }
}