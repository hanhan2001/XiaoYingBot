package me.xiaoying.bot.handle.webhandle;

import net.mamoe.mirai.Bot;

public class SelfHandle extends SubWebHandle {
    long id;
    String name;
    String selfImage;

    public SelfHandle(Bot bot) {
        this.name = bot.getNick();
        this.id = bot.getId();
        this.selfImage = "https://q.qlogo.cn/g?b=qq&nk=" + this.id + "&s=100";
    }

    @Override
    public String getMessage() {
        return "{" +
                "\"type\": \"Self\"," +
                "\"id\": \"" + this.id + "\"," +
                "\"name\": \"" + this.name + "\"," +
                "\"selfImage\": \"" + selfImage + "\"" +
                "}";
    }
}