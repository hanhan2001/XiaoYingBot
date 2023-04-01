package me.xiaoying.bot.utils;

import net.mamoe.mirai.message.data.*;

/**
 * 工具类 多元素消息发送处理
 */
public class MiraiUtil {

    /**
     * 转换消息类型
     * @param msg 字符串
     * @param oldMsg 被转换字符串
     * @param newMsg 需转换字符串
     * @return 转换完成字符串
     */
    public static MessageChain replace(MessageChain msg, String oldMsg, SingleMessage newMsg) {
        MessageChainBuilder result = new MessageChainBuilder();
        for (SingleMessage single : msg) {
            System.out.println(result);
            if (!(single instanceof PlainText))
                continue;

            String text = ((PlainText) single).getContent();
            if (text.contains(oldMsg)) {
                String[] s = text.split(oldMsg.replace("[", "\\["));
                for (int i = 0; i < s.length; i++) {
                    result.add(s[i]);
                    if (i < s.length - 1)
                        result.add(newMsg);
                }
                continue;
            }
            result.add(single);
        }
        return result.build();
    }

    /**
     * 获取 AT 中的对象QQ
     *
     * @param messageChain 源消息组
     * @return 获取QQ值
     */
    public static long getAtQQ(MessageChain messageChain, int get) {
        if (isMsgType(messageChain, "At", get)) {
            At at = (At) messageChain.get(get);
            return at.getTarget();
        }
        return 0;
    }


    /**
     * 判断消息是否为某个类型
     *
     * @param messageChain 消息
     * @param type 类型
     * @param get 判断组
     * @return 逻辑值
     */
    public static boolean isMsgType(MessageChain messageChain, String type, int get) {
        SingleMessage singleMessage = messageChain.get(get);
        switch (type) {
            case "At": {
                return singleMessage instanceof At;
            }
            case "AtAll" : {
                return singleMessage instanceof AtAll;
            }
            case "Image" : {
                return singleMessage instanceof Image;
            }
            case "FlashImage" : {
                return singleMessage instanceof FlashImage;
            }
        }
        return false;
    }
}