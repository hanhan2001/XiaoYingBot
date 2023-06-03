package me.xiaoying.bot.entity;

import me.xiaoying.bot.bot.XiaoYingBot;
import me.xiaoying.bot.handle.MessageHandle;

import java.util.Objects;

public class Friend extends User {
    public Friend(String name, long id) {
        super(name, id);
    }

    public Friend(long id) {
        this(Objects.requireNonNull(XiaoYingBot.getBot().getFriend(id)).getNick(), id);
    }

    @Override
    public void sendMessage(String message) {
        XiaoYingBot.getBot().getFriend(this.id).sendMessage(MessageHandle.StringToContent(message));
    }
}