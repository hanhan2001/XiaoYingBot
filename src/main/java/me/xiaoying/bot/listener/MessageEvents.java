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
//    @Autowired
//    private CommandConfig commandConfig;
//    @Autowired
//    private KeyWordService keyWordService;

//    Logger logger = XiaoYingBotApplication.getLogger();

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
//        Service.webSocket.send(SerializeUtil.serialize(event));
//
//        User sender = event.getSender();
//        String oriMsg = event.getMessage().contentToString();
//
//        // 存储数据
//        long userId = event.getSender().getId();
//        UserObj userObj = new UserObj(userId);
//        if (!userObj.isExists()) {
//            MysqlGetter mysqlGetter = new MysqlImpl("data_common_user");
//            String uuid = new DecimalFormat("000000000").format(mysqlGetter.getTableLineSize());
//            mysqlGetter.insertString(String.valueOf(userId), uuid, "", "default", "0", "0", "0");
//        }
//
//        // 是否指令模式
//        if (!commandConfig.isCommand(oriMsg)) {
//            keyWordService.keyWordMatche(event);
//            return ListeningStatus.LISTENING;
//        }
//
//        EverywhereCommand command = (EverywhereCommand) commandConfig.getCommand(oriMsg, CommandConfig.everywhereCommands);
//        if (command == null) {
//            return ListeningStatus.LISTENING;
//        }
//        //执行指令并回复结果
//        Message result = command.execute(sender, getArgs(oriMsg), event.getMessage(), event.getSubject());
//        if (result != null) {
//            event.getSubject().sendMessage(result);
//        }

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

//       Server.getPluginManager().callEvent(new GroupMessage(event));
//        URL[] urls = new URL[]{new File(System.getProperty("user.dir") + "/bin/PluginLoader.jar").toURI().toURL()};
//        URLClassLoader urlClassLoader = new URLClassLoader(urls);
//        Class<?> className = urlClassLoader.loadClass("me.xiaoying.pl.PluginLoader");
//        Object instance = className.newInstance();
//
//        // 实现方法
//        Method method = className.getMethod("onEvent", event.getClass());
//        method.invoke(instance, event);
//        Member sender = event.getSender();
//        Group group = sender.getGroup();
//        String oriMsg = event.getMessage().contentToString();
////
////        // 输出日志
//        LogUtil.groupLog(sender.getNick(), sender.getId(), group.getName(), group.getId(), String.valueOf(event.getMessage()));
////
////        //是否指令模式
////        if (!commandConfig.isCommand(oriMsg)) {
////            // 非指令处理其他业务
////            //关键词响应
////            return ListeningStatus.LISTENING;
////        }
////        GroupCommand command = (GroupCommand) commandConfig.getCommand(oriMsg, commandConfig.groupCommands);
////        if (command == null) {
////            return ListeningStatus.LISTENING;
////        }
////        //执行指令并回复结果
////        Message result = command.execute(sender, getArgs(oriMsg), event.getMessage(), event.getSubject());
////        if (result != null) {
////            event.getSubject().sendMessage(result);
////        }
////        //事件拦截 防止公共消息事件再次处理
////        event.intercept();

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
//        Member sender = event.getSender();
//
//        String oriMsg = event.getMessage().contentToString();
//
//        // 输出日志
//        LogUtil.tempLog(sender.getNick(), sender.getId(), String.valueOf(event.getMessage()));
//
//        // 是否指令模式
//        if (!commandConfig.isCommand(oriMsg)) {
//            return ListeningStatus.LISTENING;
//        }
//
//        TempMessageCommand command = (TempMessageCommand) commandConfig.getCommand(oriMsg, commandConfig.tempMsgCommands);
//        if (command == null) {
//            return ListeningStatus.LISTENING;
//        }
//        //执行指令并回复结果
//        Message result = command.execute(sender, getArgs(oriMsg), event.getMessage(), sender);
//        if (result != null) {
//            event.getSubject().sendMessage(result);
//        }
//        //事件拦截 防止公共消息事件再次处理
//        event.intercept();

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
//        Friend sender = event.getSender();
//        String oriMsg = event.getMessage().contentToString();
//
//        // 输出日志
//        LogUtil.friendLog(sender.getNick(), sender.getId(), String.valueOf(event.getMessage()));
//
//        // 是否指令模式
//        if (!commandConfig.isCommand(oriMsg)) {
//            return ListeningStatus.LISTENING;
//        }
//        FriendCommand command = (FriendCommand) commandConfig.getCommand(oriMsg, commandConfig.friendCommands);
//        if (command == null) {
//            return ListeningStatus.LISTENING;
//        }
//        //执行指令并回复结果
//        Message result = command.execute(sender, getArgs(oriMsg), event.getMessage(), event.getSubject());
//        if (result != null) {
//            event.getSubject().sendMessage(result);
//        }
//        //事件拦截 防止everywhere消息事件再次处理
//        event.intercept();

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