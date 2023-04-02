package me.xiaoying.bot.plugin;

import me.xiaoying.bot.command.Command;

import java.util.List;

public class PluginDescriptionFile {
    String pluginName;
    String version;
    List<String> authors;
    List<Command> commands;
    String main;

    public PluginDescriptionFile(String pluginName, String version, List<String> authors, String main) {
        this.pluginName = pluginName;
        this.version = version;
        this.authors = authors;
        this.main = main;
    }

    public PluginDescriptionFile(String pluginName, String version, List<String> authors, List<Command> commands, String main) {
        this.pluginName = pluginName;
        this.commands = commands;
        this.version = version;
        this.authors = authors;
        this.main = main;
    }

    public String getName() {
        return this.pluginName;
    }

    public String getVersion() {
        return this.version;
    }

    public List<String> getAuthors() {
        return this.authors;
    }

    public String getMain() {
        return this.main;
    }

    public List<Command> getCommands() {
        return this.commands;
    }
}