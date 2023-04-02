package me.xiaoying.bot.event;

import me.xiaoying.bot.entity.Friend;
import me.xiaoying.bot.handle.MessageHandle;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.OnlineMessageSource;

/**
 * 事件 好友消息
 */
public class FriendMessageEvent extends FriendEvent {
    net.mamoe.mirai.event.events.FriendMessageEvent event;

    public FriendMessageEvent(User user, net.mamoe.mirai.event.events.FriendMessageEvent event) {
        super(user);
        this.event = event;
    }

    public String getMessage() {
        return this.event.getMessage().contentToString();
    }

    public void sendMessage(String message) {
        this.event.getFriend().sendMessage(MessageHandle.StringToContent(message));
    }

    public Friend getSender() {
        return new Friend(this.event.getSender().getRemark(), this.event.getSender().getId());
    }

    public OnlineMessageSource.Incoming.FromFriend getSource() {
        return this.event.getSource();
    }

    public net.mamoe.mirai.contact.Friend getSubject() {
        return this.event.getSubject();
    }

    public int getTime() {
        return this.event.getTime();
    }
}