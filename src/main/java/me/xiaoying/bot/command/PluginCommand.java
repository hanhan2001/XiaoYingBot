package me.xiaoying.bot.command;

/**
 * 命令
 */
public class PluginCommand {
    Command command;
    CommandExecutor executor;

    public PluginCommand(Command command, CommandExecutor executor) {
        this.command = command;
        this.executor = executor;
    }

    public Command getCommand() {
        return command;
    }

    public CommandExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }
}