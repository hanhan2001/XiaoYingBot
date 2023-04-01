package me.xiaoying.bot.contact;

import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.User;

/**
 * 实体 发送者
 */
public class Sender {
    User user;
    Member member;
    Friend friend;

    public Sender(User user) {
        this.user = user;
    }

    public Sender(Member member) {
        this.member = member;
    }

    public Sender(Friend friend) {
        this.friend = friend;
    }

    public long getId() {
        if (user != null)
            return user.getId();
        if (member != null)
            return member.getId();
        if (friend != null)
            return friend.getId();

        return 0;
    }

    public String getNick() {
        if (user != null)
            return user.getNick();
        if (member != null)
            return member.getNick();
        if (friend != null)
            return friend.getNick();

        return null;
    }

    public void sendMessage(String message) {
        if (user != null)
            user.sendMessage(message);
        if (member != null)
            member.sendMessage(message);
        if (friend != null)
            friend.sendMessage(message);
    }
}