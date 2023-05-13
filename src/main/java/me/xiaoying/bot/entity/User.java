package me.xiaoying.bot.entity;

import me.xiaoying.bot.api.XiaoYing;
import me.xiaoying.bot.bot.XiaoYingBot;
import me.xiaoying.bot.handle.MessageHandle;

import java.util.Objects;

public class User {
    long id;
    String name;

    public User(net.mamoe.mirai.contact.User user) {
        this.name = user.getNick();
        this.id = user.getId();
    }

    public User(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void sendMessage(String message) {
        Objects.requireNonNull(XiaoYingBot.getBot().getStranger(this.id)).sendMessage(MessageHandle.StringToContent(message));
    }

    public boolean hasPermission(String permission) {
        XiaoYing.getPermissionService().hasPermission(this, permission);
        return false;
    }

    public String getName() {
        return name;
    }
}