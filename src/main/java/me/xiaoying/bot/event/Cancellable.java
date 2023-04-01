package me.xiaoying.bot.event;

public interface Cancellable {
    boolean isCancelled();

    void setCancelled(boolean cancelled);
}