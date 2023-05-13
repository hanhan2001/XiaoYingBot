package me.xiaoying.bot.entity;

import me.xiaoying.bot.bot.XiaoYingBot;

import java.util.Objects;

public class CommandSender extends User {
    Group group = null;

    public CommandSender(String name, long id, Group group) {
        super(name, id);
        this.group = group;
    }

    public CommandSender(String name, long id) {
        super(name, id);
    }

    public Group getGroup() {
        return this.group;
    }

    /**
     * 向命令触发者 所在群聊/本人 发送信息<p>
     * 当触发者不在 群聊 内则发送给 本人<p>
     * 也可用方法 getUser().sendMessage(message::String) 向触发者发送信息
     *
     * @param message 发送内容
     */
    @Override
    public void sendMessage(String message) {
        if (this.group == null) {
            Objects.requireNonNull(XiaoYingBot.getBot().getStranger(this.id)).sendMessage(message);
            return;
        }

        this.group.sendMessage(message);
    }

    public User getUser() {
        return new User(Objects.requireNonNull(XiaoYingBot.getBot().getStranger(this.id)));
    }
}