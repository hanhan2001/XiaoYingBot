package me.xiaoying.bot.event;

import me.xiaoying.bot.entity.Member;
import me.xiaoying.bot.handle.MessageHandle;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.*;

public class GroupMessageEvent extends GroupEvent {
    Member member;

    public GroupMessageEvent(net.mamoe.mirai.event.events.GroupMessageEvent event) {
        super(event);
        member = new Member(event.getSenderName(), event.getSender().getId());
    }

    public MessageChain getMessageChain() {
        return this.event.getMessage();
    }

    public String getMessage() {
        return this.event.getMessage().contentToString();
    }

    public Group getGroup() {
        return this.event.getGroup();
    }

    public Bot getBot() {
        return this.event.getBot();
    }

    public void sendMessage(String message) {
        event.getGroup().sendMessage(MessageHandle.StringToContent(message));
    }

    public Member getSender() {
        return this.member;
    }

    public OnlineMessageSource.Incoming.FromGroup getSource() {
        return this.event.getSource();
    }

    public Group getSubject() {
        return this.event.getSubject();
    }

    public int getTime() {
        return this.event.getTime();
    }
}
