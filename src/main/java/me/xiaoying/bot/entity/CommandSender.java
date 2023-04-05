package me.xiaoying.bot.entity;

public class CommandSender extends User {
    Group group;

    public CommandSender(String name, long id, Group group) {
        super(name, id);
        this.group = group;
    }

    public Group getGroup() {
        return this.group;
    }
}