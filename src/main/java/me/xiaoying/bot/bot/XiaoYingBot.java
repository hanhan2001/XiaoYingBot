package me.xiaoying.bot.bot;

import me.xiaoying.bot.api.XiaoLoginSolver;
import me.xiaoying.bot.enums.InfoType;
import me.xiaoying.bot.file.FileConfig;
import me.xiaoying.bot.listener.BotEvents;
import me.xiaoying.bot.listener.GroupEvent;
import me.xiaoying.bot.listener.MessageEvents;
import me.xiaoying.bot.utils.EncryptUtil;
import me.xiaoying.bot.utils.InfoUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.ListenerHost;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class XiaoYingBot {
    // 一个实例只给一个bot，暂时不考虑一个实例允许部署多个bot
    private static Bot bot;

    public static Bot getBot() {
        return bot;
    }

    // 监听事件
    @Autowired
    private MessageEvents messageEvents;
    @Autowired
    private BotEvents botEvents;
    @Autowired
    private GroupEvent groupEvents;

    // 账号
    private static final Long account = FileConfig.BOT_ACCOUNT;
    // 密码
    private static String password = FileConfig.BOT_PASSWORD;

    // 设备认证信息
    private static final String deviceInfo = FileConfig.BOT_DEVICES;

    // 启动Bot
    public void startBot() {
        if (FileConfig.BOT_AUTHORIZE.equalsIgnoreCase("password") && (0 == account || null == password)) {
            InfoUtil.sendMessage(InfoType.WARING, "账号或密码为空！！");
            System.exit(0);
        }

        if (FileConfig.BOT_ENCRYPT) {
            InfoUtil.sendMessage("正在解密密码");
            password = EncryptUtil.base64Decrypt(password);
            InfoUtil.sendMessage("解密密码完成");
        }

        switch (FileConfig.BOT_AUTHORIZE.toUpperCase()) {
            case "PASSWORD":
                bot = BotFactory.INSTANCE.newBot(account, password, new BotConfiguration());
                break;
            case "QRCODE":
                bot = BotFactory.INSTANCE.newBot(account, BotAuthorization.byQRCode(), new BotConfiguration());
                break;
        }

        // 保存设备信息到文件 deviceInfo.json 文件里相当于是个设备认证信息
        bot.getConfiguration().fileBasedDeviceInfo(deviceInfo);
        bot.getConfiguration().setLoginSolver(new XiaoLoginSolver());

        // 取消日志
        bot.getConfiguration().noBotLog();
        bot.getConfiguration().noNetworkLog();

        // 列表缓存
        bot.getConfiguration().enableContactCache();

        // 协议选择
        switch (FileConfig.BOT_PROTOCOl) {
            case "ANDROID_PHONE": {
                bot.getConfiguration().setProtocol(BotConfiguration.MiraiProtocol.ANDROID_PHONE);
                break;
            }
            case "ANDROID_PAD": {
                bot.getConfiguration().setProtocol(BotConfiguration.MiraiProtocol.ANDROID_PAD);
                break;
            }
            case "ANDROID_WATCH": {
                bot.getConfiguration().setProtocol(BotConfiguration.MiraiProtocol.ANDROID_WATCH);
                break;
            }
            case "IPAD": {
                bot.getConfiguration().setProtocol(BotConfiguration.MiraiProtocol.IPAD);
                break;
            }
            case "MACOS": {
                bot.getConfiguration().setProtocol(BotConfiguration.MiraiProtocol.MACOS);
                break;
            }
            default: {
                InfoUtil.sendMessage(InfoType.WARING, "错误的协议！！！请更换设备协议！！！");
                System.exit(0);
            }
        }

        InfoUtil.sendMessage("使用协议: " + FileConfig.BOT_PROTOCOl);
        InfoUtil.sendMessage("开始尝试登录");
        bot.login();
        InfoUtil.sendMessage(bot.getBot().getId() + " 已登录");

        // 注册消息事件
        InfoUtil.sendMessage("注册消息事件");

        List<ListenerHost> events = Collections.singletonList(messageEvents);
        for (ListenerHost event : events)
            GlobalEventChannel.INSTANCE.registerListenerHost(event);

        List<ListenerHost> botEvent = Collections.singletonList(botEvents);
        for (ListenerHost event : botEvent)
            GlobalEventChannel.INSTANCE.registerListenerHost(event);

        List<ListenerHost> groupEvent = Collections.singletonList(botEvents);
        for (ListenerHost event : groupEvent)
            GlobalEventChannel.INSTANCE.registerListenerHost(event);

        InfoUtil.sendMessage("注册完成");

        // 设置https协议，已解决SSL peer shut down incorrectly的异常
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");

        // 这个和picbotx 还是不太一样 那个不会占用主线程
        // 这里必须要启新线程去跑bot 不然会占用主线程
        new Thread(() -> bot.join()).start();
    }
}