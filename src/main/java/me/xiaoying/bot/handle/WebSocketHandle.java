package me.xiaoying.bot.handle;

import me.xiaoying.bot.cache.Caches;
import me.xiaoying.bot.constant.ConstantCommon;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

/**
 * 服务 Websocket
 */
public class WebSocketHandle extends WebSocketServer {
    public WebSocketHandle(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        webSocket.send("url " + ConstantCommon.QQ_AUTHORIZE_HREF);
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {

    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        if (!s.startsWith("confirm "))
            return;
        String ticket = s.replace("confirm ", "");
        if (ticket.isEmpty())
            return;

        ConstantCommon.QQ_AUTHORIZE_TICKET = s.replace("confirm ", "");
        Caches.threadCaches.get("thread-login-solver").stop();
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {

    }
}