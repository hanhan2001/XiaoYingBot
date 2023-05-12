package me.xiaoying.bot.entity;

import me.xiaoying.bot.api.XiaoYing;

public class GroupUser extends User {
    Group group;

    public GroupUser(Group group, String name, long id) {
        super(name, id);
        this.group = group;
    }

    public Group getGroup() {
        return this.group;
    }

    @Override
    public boolean hasPermission(String permission) {
        return XiaoYing.getPermissionService().hasPermission(this.group, this, permission);
    }
}