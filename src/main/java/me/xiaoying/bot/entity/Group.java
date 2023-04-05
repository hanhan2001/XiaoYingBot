package me.xiaoying.bot.entity;

import me.xiaoying.bot.bot.XiaoYingBot;
import me.xiaoying.bot.handle.MessageHandle;
import net.mamoe.mirai.contact.ContactList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group {
    long id;
    List<Member> members;
    String name;
    Member owner;

    public Group(long id) {
        if (XiaoYingBot.getBot().getGroup(id) == null)
            return;

        this.id = id;

        List<Member> members = new ArrayList<>();
        Objects.requireNonNull(XiaoYingBot.getBot().getGroup(this.id)).getMembers().forEach(m -> {
            Member member = new Member(m.getNameCard(), m.getId());
            members.add(member);
        });
        this.members = members;

        this.name = Objects.requireNonNull(XiaoYingBot.getBot().getGroup(this.id)).getName();
        this.owner = new Member(Objects.requireNonNull(XiaoYingBot.getBot().getGroup(id)).getOwner().getNameCard(), Objects.requireNonNull(XiaoYingBot.getBot().getGroup(id)).getOwner().getId());
    }

    public Group(net.mamoe.mirai.contact.Group group) {
        this.id = group.getId();

        List<Member> members = new ArrayList<>();
        group.getMembers().forEach(m -> {
            Member member = new Member(m.getNameCard(), m.getId());
            members.add(member);
        });
        this.members = members;
        this.name = group.getName();
        this.owner = new Member(group.getOwner().getNameCard(), group.getOwner().getId());
    }

    public long getId() {
        return this.id;
    }

    public void sendMessage(String message) {
        Objects.requireNonNull(XiaoYingBot.getBot().getGroup(this.id)).sendMessage(MessageHandle.StringToContent(message));
    }

    public List<Member> getMembers() {
        List<Member> list = new ArrayList<>();
        Objects.requireNonNull(XiaoYingBot.getBot().getGroup(this.id)).getMembers().forEach(m -> {
            Member member = new Member(m.getNameCard(), m.getId());
            list.add(member);
        });
        return list;
    }

    public String getName() {
        return Objects.requireNonNull(XiaoYingBot.getBot().getGroup(this.id)).getName();
    }

    public Member getOwner() {
        return this.owner;
    }

    public boolean contains(long id) {
        for (Member member : members) {
            if (id == member.getId())
                return true;
        }
        return false;
    }

    public boolean contains(Member member) {
        return members.contains(member);
    }

    public Member get(long id) {
        return new Member(Objects.requireNonNull(Objects.requireNonNull(XiaoYingBot.getBot().getGroup(this.id)).get(id)).getNameCard(), id);
    }
}