package me.xiaoying.bot.event;

import me.xiaoying.bot.api.XiaoYing;
import me.xiaoying.bot.bot.XiaoYingBot;
import me.xiaoying.bot.entity.Member;
import me.xiaoying.bot.handle.MessageHandle;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.*;

import java.util.Date;
import java.util.Objects;

public class GroupMessageEvent extends GroupEvent {
    Member member;
    MessageChain message;

    public GroupMessageEvent(me.xiaoying.bot.entity.Group group, Member member, MessageChain message) {
        super(group);
        this.member = member;
        this.message = message;
    }

    public MessageChain getMessageChain() {
        return this.message;
    }

    public String getMessage() {
        return this.message.contentToString();
    }

    public me.xiaoying.bot.entity.Group getGroup() {
        return this.group;
    }

//    public Bot getBot() {
//        return this.;
//    }

    public void sendMessage(String message) {
        Objects.requireNonNull(XiaoYingBot.getBot().getGroup(this.group.getId())).sendMessage(MessageHandle.StringToContent(message));
    }

    public Member getSender() {
        return this.member;
    }

    public OnlineMessageSource.Incoming.FromGroup getSource() {
        return (OnlineMessageSource.Incoming.FromGroup) XiaoYingBot.getBot().getGroup(this.group.getId());
    }

    public Group getSubject() {
        return XiaoYingBot.getBot().getGroup(this.group.getId());
    }

    public int getTime() {
        return (int) (new Date().getTime());
    }
}
