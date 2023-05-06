package me.xiaoying.bot.bot;

import me.xiaoying.bot.enums.InfoType;
import me.xiaoying.bot.utils.InfoUtil;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.Nullable;

public class XiaoYingLogger implements MiraiLogger {
    @Nullable
    @Override
    public String getIdentity() {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void debug(@Nullable String s) {
        InfoUtil.sendMessage(InfoType.DEBUG, s);
    }

    @Override
    public void debug(@Nullable String s, @Nullable Throwable throwable) {
        InfoUtil.sendMessage(InfoType.DEBUG, s);
        if (throwable == null)
            return;
        throwable.printStackTrace();
    }

    @Override
    public void error(@Nullable String s) {
        InfoUtil.sendMessage(InfoType.ERROR, s);
    }

    @Override
    public void error(@Nullable String s, @Nullable Throwable throwable) {
        InfoUtil.sendMessage(InfoType.ERROR, s);
        if (throwable == null)
            return;
        throwable.printStackTrace();
    }

    @Override
    public void info(@Nullable String s) {
        InfoUtil.sendMessage(InfoType.INFO, s);
    }

    @Override
    public void info(@Nullable String s, @Nullable Throwable throwable) {
        InfoUtil.sendMessage(InfoType.INFO, s);
        if (throwable == null)
            return;
        throwable.printStackTrace();
    }

    @Override
    public void verbose(@Nullable String s) {
        InfoUtil.sendMessage(InfoType.INFO, s);
    }

    @Override
    public void verbose(@Nullable String s, @Nullable Throwable throwable) {
        InfoUtil.sendMessage(InfoType.INFO, s);
        if (throwable == null)
            return;
        throwable.printStackTrace();
    }

    @Override
    public void warning(@Nullable String s) {
        InfoUtil.sendMessage(InfoType.WARING, s);
    }

    @Override
    public void warning(@Nullable String s, @Nullable Throwable throwable) {
        InfoUtil.sendMessage(InfoType.WARING, s);
        if (throwable == null)
            return;
        throwable.printStackTrace();
    }
}
