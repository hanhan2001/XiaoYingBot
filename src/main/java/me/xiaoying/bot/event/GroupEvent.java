package me.xiaoying.bot.event;

import me.xiaoying.bot.entity.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.internal.deps.io.ktor.client.plugins.Sender;

public class GroupEvent extends Event {
    protected Sender sender;

    Group group;

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public GroupEvent(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
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
