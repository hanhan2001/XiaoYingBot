package me.xiaoying.bot.event;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.internal.deps.io.ktor.client.plugins.Sender;

public class GroupEvent extends Event {
    protected Sender sender;
    GroupMessageEvent event;
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public GroupEvent(GroupMessageEvent event) {
        super();
        this.event = event;
    }

    @Override
    public boolean isCancelled() {
        return super.isCancelled();
    }

    @Override
    public void setCancelled(boolean cancelled) {
        super.setCancelled(cancelled);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public String getEventName() {
        return super.getEventName();
    }
}
