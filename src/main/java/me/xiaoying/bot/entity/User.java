package me.xiaoying.bot.entity;

import me.xiaoying.bot.api.XiaoYing;

public class User {
    long id;
    String name;

    public User(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public boolean hasPermission(String permission) {
        return XiaoYing.getPermissionService().hasPermission(this, permission);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}