package me.xiaoying.bot.listener;

import me.xiaoying.bot.XiaoYingBotApplication;
import me.xiaoying.bot.file.FileConfig;
import me.xiaoying.bot.utils.*;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MessageEvents extends SimpleListenerHost {
    /**
     * 所有消息处理
     *
     * @param event 消息事件
     * @return 监听状态 详见 ListeningStatus
     * @throws Exception 可以抛出任何异常, 将在 handleException 处理
     */
    @NotNull
    @EventHandler
    public ListeningStatus onMessage(@NotNull MessageEvent event) throws Exception {
        XiaoYingBotApplication.server.getPluginManager().callEvent(new me.xiaoying.bot.event.MessageEvent(event));
        //事件拦截 防止everywhere消息事件再次处理
        event.intercept();
        return ListeningStatus.LISTENING;
    }

    /**
     * 群聊消息事件处理
     *
     * @param event 消息事件
     * @return 监听状态 详见 ListeningStatus
     * @throws Exception 可以抛出任何异常, 将在 handleException 处理
     */
    @NotNull
    @EventHandler
    public ListeningStatus onGroupMessage(@NotNull GroupMessageEvent event) throws Exception {
        if (event.getMessage().contentToString().equalsIgnoreCase(".重载")) {
            XiaoYingBotApplication.server.reload();
            event.getGroup().sendMessage("重载完成");
        }

        String log = FileConfig.SET_MESSAGE_LOG_GROUP;
        log = log.replace("%date%", DateUtil.date(FileConfig.SET_VARIABLE_DATA));
        log = log.replace("%groupName%", event.getGroup().getName());
        log = log.replace("%groupId%", String.valueOf(event.getGroup().getId()));
        log = log.replace("%name%", event.getSenderName());
        log = log.replace("%qq%", String.valueOf(event.getSender().getId()));
        log = log.replace("%msg%", event.getMessage().contentToString());
        System.out.println(log.replace("\n" , "     \n"));

        XiaoYingBotApplication.server.getPluginManager().callEvent(new me.xiaoying.bot.event.GroupMessageEvent(event));
        //事件拦截 防止everywhere消息事件再次处理
        event.intercept();
        return ListeningStatus.LISTENING;
    }

    /**
     * 群临时消息事件处理
     *
     * @param event 消息事件
     * @return 监听状态 详见 ListeningStatus
     * @throws Exception 可以抛出任何异常, 将在 handleException 处理
     */
    @NotNull
    @EventHandler
    public ListeningStatus onTempMessage(@NotNull TempMessageEvent event) throws Exception {

        String log = FileConfig.SET_MESSAGE_LOG_TEMP;
        log = log.replace("%date%", DateUtil.date(FileConfig.SET_VARIABLE_DATA));
        log = log.replace("%name%", event.getSenderName());
        log = log.replace("%qq%", String.valueOf(event.getSender().getId()));
        log = log.replace("%msg%", event.getMessage().contentToString());
        System.out.println(log.replace("\n" , "     \n"));
        //事件拦截 防止公共消息事件再次处理
        event.intercept();
        return ListeningStatus.LISTENING;
    }

    /**
     * 好友私聊消息事件处理
     *
     * @param event 消息事件
     * @return 监听状态 详见 ListeningStatus
     * @throws Exception 可以抛出任何异常, 将在 handleException 处理
     */
    @NotNull
    @EventHandler
    public ListeningStatus onFriendMessage(@NotNull FriendMessageEvent event) throws Exception {
        String log = FileConfig.SET_MESSAGE_LOG_PRIVATE;
        log = log.replace("%date%", DateUtil.date(FileConfig.SET_VARIABLE_DATA));
        log = log.replace("%name%", event.getSenderName());
        log = log.replace("%qq%", String.valueOf(event.getSender().getId()));
        log = log.replace("%msg%", event.getMessage().contentToString());
        System.out.println(log.replace("\n" , "     \n"));
        XiaoYingBotApplication.server.getPluginManager().callEvent(new me.xiaoying.bot.event.FriendMessageEvent(event.getUser(), event));
        //事件拦截 防止everywhere消息事件再次处理
        event.intercept();
        return ListeningStatus.LISTENING;
    }

    /**
     * 从消息体中获得 用空格分割的参数
     *
     * @param msg 消息
     * @return 分割出来的参数
     */
    private ArrayList<String> getArgs(String msg) {
        String[] args = msg.trim().split(" ");
        ArrayList<String> list = new ArrayList<String>();
        for (String arg : args) {
            if (!StringUtil.isEmpty(arg)) list.add(arg);
        }
        list.remove(0);
        return list;
    }
}