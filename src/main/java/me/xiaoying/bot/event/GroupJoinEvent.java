package me.xiaoying.bot.event;

import me.xiaoying.bot.entity.Group;
import me.xiaoying.bot.entity.User;

public class GroupJoinEvent extends GroupEvent {
    User user;

    public GroupJoinEvent(Group group, User user) {
        super(group);
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
