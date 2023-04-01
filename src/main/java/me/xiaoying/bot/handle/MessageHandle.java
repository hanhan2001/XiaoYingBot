package me.xiaoying.bot.handle;

import me.xiaoying.bot.utils.LongUtil;
import me.xiaoying.bot.utils.MiraiUtil;
import me.xiaoying.bot.utils.StringUtil;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.util.List;

public class MessageHandle {
    public static MessageChain StringToContent(String message) {
        MessageChain messageChain = new MessageChainBuilder().append(message).build();

        List<String> strings = StringUtil.rexStr(message, "%", "%");
        for (String string : strings) {
            if (string.startsWith("%at=")) {
                string = StringUtil.removeSomeString(string, 0);
                string = StringUtil.removeSomeString(string, 0);
                string = StringUtil.removeSomeString(string, 0);
                string = StringUtil.removeSomeString(string, 0);
                string = StringUtil.removeSomeString(string, string.split("").length - 1);
                if (!LongUtil.isLong(string))
                    continue;
                messageChain = MiraiUtil.replace(messageChain, "%at=" + string + "%", new At(Long.parseLong(string)));
            }
        }
        return messageChain;
    }
}