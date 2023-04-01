package me.xiaoying.bot.event;

public abstract class Event implements Cancellable {
    String name;
    boolean cancelled;

    public Event() {

    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public abstract HandlerList getHandlers();

    public String getEventName() {
        if (name == null) {
            this.name = this.getClass().getSimpleName();
        }
        return this.name;
    }
}