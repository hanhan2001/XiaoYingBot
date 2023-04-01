package me.xiaoying.bot.plugin;

public abstract class PluginBase implements Plugin {
    public PluginBase() {
    }

    public final int hashCode() {
        return this.getName().hashCode();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else {
            return !(obj instanceof Plugin) ? false : this.getName().equals(((Plugin)obj).getName());
        }
    }

    public final String getName() {
        return this.getDescription().getName();
    }
}