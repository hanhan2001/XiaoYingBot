package me.xiaoying.bot.event;

import me.xiaoying.bot.entity.User;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.OnlineMessageSource;

/**
 * 事件 聊天
 */
public class MessageEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    net.mamoe.mirai.event.events.MessageEvent event;

    public MessageEvent(net.mamoe.mirai.event.events.MessageEvent event) {
        this.event = event;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getMessage() {
        return this.event.getMessage().contentToString();
    }

    public User getSender() {
        return new User(this.event.getSenderName(), this.event.getSender().getId());
    }

    public String getSenderName() {
        return this.event.getSenderName();
    }

    public OnlineMessageSource.Incoming getSource() {
        return this.event.getSource();
    }

    public Contact getSubject() {
        return this.event.getSubject();
    }

    public int getTime() {
        return this.event.getTime();
    }
}