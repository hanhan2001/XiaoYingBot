package me.xiaoying.bot.message;

public class MessageChainBuilder {
    public final net.mamoe.mirai.message.data.MessageChainBuilder append(CharSequence message) {
        return new net.mamoe.mirai.message.data.MessageChainBuilder().append(message);
    }

    public final net.mamoe.mirai.message.data.MessageChainBuilder append(Message message) {
        return new net.mamoe.mirai.message.data.MessageChainBuilder().append(message);
    }
}