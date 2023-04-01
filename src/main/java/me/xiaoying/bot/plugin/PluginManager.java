package me.xiaoying.bot.plugin;

import me.xiaoying.bot.event.Event;
import me.xiaoying.bot.event.EventExecutor;
import me.xiaoying.bot.event.EventPriority;
import me.xiaoying.bot.event.Listener;

import java.io.File;

public interface PluginManager {
    void registerInterface(Class<? extends PluginLoader> loader) throws IllegalArgumentException;

    Plugin getPlugin(String plugin);

    Plugin[] getPlugins();

    boolean isPluginEnabled(String plugin);

    boolean isPluginEnabled(Plugin plugin);

    Plugin loadPlugin(File file) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException;

    Plugin[] loadPlugins(File file);

    void disablePlugins();

    void clearPlugins();

    void callEvent(Event event) throws IllegalStateException;

    void registerEvents(Listener listener, Plugin plugin);

    void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin);

    void enablePlugin(Plugin plugin);

    void disablePlugin(Plugin plugin);
}