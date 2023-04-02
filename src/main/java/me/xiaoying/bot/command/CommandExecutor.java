package me.xiaoying.bot.command;

import me.xiaoying.bot.entity.CommandSender;

/**
 * 命令 处理接口
 */
public interface CommandExecutor {
    boolean onCommand(CommandSender sender, Command command, String[] args);
}