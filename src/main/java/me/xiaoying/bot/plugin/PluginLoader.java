package me.xiaoying.bot.plugin;

import me.xiaoying.bot.event.Event;
import me.xiaoying.bot.event.Listener;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public interface PluginLoader {
    Plugin loadPlugin(File file) throws InvalidPluginException;

    PluginDescriptionFile getPluginDescription(File file) throws InvalidDescriptionException;

    Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(Listener listener, Plugin plugin);

    Pattern[] getPluginFileFilters();

    void delRegisteredListener(Listener listener);
    void delRegisteredListener(Plugin plugin);
    void delRegisteredListeners();
    List<RegisteredListener> getRegisteredListener();

    void enablePlugin(Plugin plugin);

    void disablePlugin(Plugin plugin);
}