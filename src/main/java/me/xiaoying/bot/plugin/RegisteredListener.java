package me.xiaoying.bot.plugin;

import me.xiaoying.bot.event.*;

public class RegisteredListener {
    private final Listener listener;
    private final EventPriority priority;
    private final EventExecutor executor;
    private final Plugin plugin;

    public RegisteredListener(Listener listener, EventExecutor executor, EventPriority priority, Plugin plugin) {
        this.listener = listener;
        this.plugin = plugin;
        this.executor = executor;
        this.priority = priority;
//        this.method = method;
    }

    public void callEvent(Event event) throws EventException {
        if (event == null || !((Cancellable)event).isCancelled()) {
            this.executor.execute(this.listener, event);
        }
    }

    public Listener getListener() {
        return this.listener;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public EventPriority getPriority() {
        return this.priority;
    }
}