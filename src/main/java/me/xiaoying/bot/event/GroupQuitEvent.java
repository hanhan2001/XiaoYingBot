package me.xiaoying.bot.event;

import me.xiaoying.bot.entity.Group;
import me.xiaoying.bot.entity.User;

public class GroupQuitEvent extends GroupEvent {
    User user;

    public GroupQuitEvent(Group group, User user) {
        super(group);
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
