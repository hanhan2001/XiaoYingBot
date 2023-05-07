package me.xiaoying.bot.entity;

import me.xiaoying.bot.api.XiaoYing;

public class User {
    long id;
    String name;

    public User(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public boolean hasPermission(String permission) {
        XiaoYing.getPermissionService().hasPermission(this, permission);
        return false;
    }

    public String getName() {
        return name;
    }
}