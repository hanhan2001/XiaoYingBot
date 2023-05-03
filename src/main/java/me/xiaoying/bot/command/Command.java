package me.xiaoying.bot.command;

import me.xiaoying.bot.plugin.Plugin;

import java.util.List;

/**
 * 命令 命令
 */
public class Command {
    private final String name;
    private final List<String> alias;

    public Command(String name) {
        this.name = name;
        this.alias = null;
    }

    public Command(String name, List<String> alias) {
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public List<String> getAlias() {
        return alias;
    }
}