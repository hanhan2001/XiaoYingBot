package me.xiaoying.bot.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.xiaoying.bot.cache.Caches;
import me.xiaoying.bot.constant.ConstantCommon;
import me.xiaoying.bot.entity.Friend;
import me.xiaoying.bot.entity.Group;
import me.xiaoying.bot.entity.User;
import me.xiaoying.bot.handle.webhandle.SubWebHandle;
import me.xiaoying.bot.utils.JSONUtil;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

/**
 * 服务 Websocket
 */
public class WebSocketHandle extends WebSocketServer {
    WebSocket webSocket;

    public WebSocketHandle(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        this.webSocket = webSocket;
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {

    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        loginConfirm(s);

        System.out.println(s);
        if (!JSONUtil.isJSON(s))
            return;

        JSONObject jsonObject = JSONObject.parseObject(s);
        String type = jsonObject.getString("type");
        long id = Long.parseLong(jsonObject.getString("id"));
        String message = jsonObject.getString("message");
        if (type.equalsIgnoreCase("GroupMessage")) {
            Group group = new Group(id);
            group.sendMessage(message);
            return;
        }
        if (type.equalsIgnoreCase("FriendMessage")) {
            Friend friend = new Friend(id);
            friend.sendMessage(message);
        }
    }

    private void loginConfirm(String message) {
        if (!message.startsWith("confirm "))
            return;
        String ticket = message.replace("confirm ", "");
        if (ticket.isEmpty())
            return;

        ConstantCommon.QQ_AUTHORIZE_TICKET = message.replace("confirm ", "");
        Caches.threadCaches.get("thread-login-solver").interrupt();
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {

    }

    public void sendMessage(String message) {
        if (this.webSocket == null)
            return;

        this.webSocket.send(message);
    }

    public void sendMessage(SubWebHandle webHandle) {
        if (this.webSocket == null)
            return;

        this.webSocket.send(webHandle.getMessage());
    }
}