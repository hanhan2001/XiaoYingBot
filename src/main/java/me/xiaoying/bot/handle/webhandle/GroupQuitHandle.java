package me.xiaoying.bot.handle.webhandle;

import me.xiaoying.bot.entity.Group;
import me.xiaoying.bot.entity.User;

public class GroupQuitHandle extends SubWebHandle {
    String message;

    public GroupQuitHandle(Group group, User user) {
        this.message = "{\n" +
                "\"type\": \"GroupQuit\",\n" +
                "\"group\": \"" + group.getId() + "\",\n" +
                "\"groupName\": \"" + group.getName() + "\",\n" +
                "\"user\": \"" + user.getId() + "\",\n" +
                "\"userName\": \"" + user.getName() + "\"\n" +
                "}";
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}