package me.xiaoying.bot.handle.webhandle;

import me.xiaoying.bot.entity.Group;
import me.xiaoying.bot.entity.User;

public class GroupMessageHandle extends SubWebHandle {
    String message;

    public GroupMessageHandle(Group group, User user, String message) {
        this.message = "{\n" +
                "\"type\": \"GroupMessage\",\n" +
                "\"group\": \"" + group.getId() + "\",\n" +
                "\"groupName\": \"" + group.getName() + "\",\n" +
                "\"user\": \"" + user.getId() + "\",\n" +
                "\"userName\": \"" + user.getName() + "\",\n" +
                "\"message\": \"" + message.replace("\n", "<br>") + "\"\n" +
                "}";
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}