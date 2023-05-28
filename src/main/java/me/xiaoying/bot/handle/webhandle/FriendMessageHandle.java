package me.xiaoying.bot.handle.webhandle;

import me.xiaoying.bot.entity.User;

public class FriendMessageHandle extends SubWebHandle {
    String message;

    public FriendMessageHandle(User user, String message) {
        this.message = "{\n" +
                "\"type\": \"FriendMessage\",\n" +
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