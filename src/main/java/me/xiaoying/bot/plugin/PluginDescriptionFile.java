package me.xiaoying.bot.plugin;

import java.util.List;

public class PluginDescriptionFile {
    public String pluginName;
    public String version;
    public List<String> authors;
    public String main;

    public PluginDescriptionFile(String pluginName, String version, List<String> authors, String main) {
        this.pluginName = pluginName;
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
}