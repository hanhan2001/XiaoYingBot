package me.xiaoying.bot.event;


import me.xiaoying.bot.entity.User;

/**
 * 事件 好友
 */
public class FriendEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    User user;

    public FriendEvent(net.mamoe.mirai.contact.User user) {
        this.user = new me.xiaoying.bot.entity.User(user.getRemark(), user.getId());
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public String getEventName() {
        return super.getEventName();
    }

    @Override
    public void setCancelled(boolean cancelled) {
        super.setCancelled(cancelled);
    }

    @Override
    public boolean isCancelled() {
        return super.isCancelled();
    }

    public me.xiaoying.bot.entity.User getUser() {
        return user;
    }
}