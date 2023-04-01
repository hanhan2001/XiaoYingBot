package me.xiaoying.bot.event;

import me.xiaoying.bot.plugin.Plugin;

public interface EventExecutor {
    void execute(Listener paramListener, Event paramEvent) throws EventException;
}