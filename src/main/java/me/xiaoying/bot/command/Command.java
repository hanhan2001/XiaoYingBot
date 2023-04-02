package me.xiaoying.bot.command;

import java.util.List;

public class Command {
    private String name;
    private List<String> aliases;

    public Command() {
    }

    public Command(String name) {
        this.name = name;
        this.aliases = null;
    }

    public Command(String name, List<String> aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    public String getName() {
        return name;
    }

    public List<String> getAliases() {
        return aliases;
    }
}