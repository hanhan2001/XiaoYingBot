package me.xiaoying.bot.entity;

import me.xiaoying.bot.api.XiaoYing;

public class Member extends User {
    Group group;
    public Member(String name, long id) {
        super(name, id);
    }

    public Group getGroup() {
        return this.group;
    }

    @Override
    public boolean hasPermission(String permission) {
        return XiaoYing.getPermissionService().hasPermission(this.group, this, permission);
    }
}