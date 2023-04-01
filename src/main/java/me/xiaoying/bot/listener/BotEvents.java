package me.xiaoying.bot.listener;

import me.xiaoying.bot.cache.Caches;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BotOnlineEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class BotEvents extends SimpleListenerHost {
    @NotNull
    @EventHandler
    public ListeningStatus onBotLogin(BotOnlineEvent event) {
        return ListeningStatus.LISTENING;
    }
}
