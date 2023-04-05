package me.xiaoying.bot.command;

import me.xiaoying.bot.entity.CommandSender;

public interface CommandExecutor {
    void onCommand(CommandSender sender, Command command, String[] args);
}