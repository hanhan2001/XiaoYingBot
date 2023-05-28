package me.xiaoying.bot.handle;

import me.xiaoying.bot.cache.Caches;
import me.xiaoying.bot.constant.ConstantCommon;
import me.xiaoying.bot.handle.webhandle.SubWebHandle;
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
        this.webSocket.send(message);
    }

    public void sendMessage(SubWebHandle webHandle) {
        this.webSocket.send(webHandle.getMessage());
    }

}