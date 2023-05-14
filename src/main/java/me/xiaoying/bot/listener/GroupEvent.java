package me.xiaoying.bot.listener;

import me.xiaoying.bot.file.FileConfig;
import me.xiaoying.bot.utils.DateUtil;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import org.springframework.stereotype.Component;

@Component
public class GroupEvent extends SimpleListenerHost {
    @EventHandler
    public ListeningStatus onMemberJoin(MemberJoinEvent event) {
        String log = FileConfig.SET_GROUP_LOG_MEMBER_JOIN;

        log = log.replace("%date%", DateUtil.date(FileConfig.SET_VARIABLE_DATA));
        log = log.replace("%groupName%", event.getGroup().getName());
        log = log.replace("%groupId%", String.valueOf(event.getGroup().getId()));
        log = log.replace("%name%", event.getMember().getNick());
        log = log.replace("%qq%", String.valueOf(event.getMember().getId()));
        System.out.println(log.replace("\n" , "     \n"));
        return ListeningStatus.LISTENING;
    }
}
