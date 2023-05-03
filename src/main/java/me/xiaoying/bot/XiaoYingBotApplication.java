package me.xiaoying.bot;

import me.xiaoying.bot.api.XiaoYing;
import me.xiaoying.bot.bot.XiaoYingBot;
import me.xiaoying.bot.file.FileConfig;
import me.xiaoying.bot.handle.WebSocketHandle;
import me.xiaoying.bot.server.BotServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@SpringBootApplication
public class XiaoYingBotApplication {
    public static BotServer server;
    public static ScheduledExecutorService executor = Executors.newScheduledThreadPool(50);

    public static void main(String[] args) {
        initialize();

        WebSocketHandle webSocketHandle = new WebSocketHandle(new InetSocketAddress(6501));
        webSocketHandle.setConnectionLostTimeout(0);
        webSocketHandle.start();
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        XiaoYingBot xiaoYingBot = context.getBean(XiaoYingBot.class);
        xiaoYingBot.startBot();
    }

    public static void initialize() {
        FileConfig.fileConfig();

        server = new BotServer();
        XiaoYing.setServer(server);
        server.loadPlugins();
        server.enablePlugins();
    }
}