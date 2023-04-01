package me.xiaoying.bot.api;

import me.xiaoying.bot.plugin.PluginManager;
import me.xiaoying.bot.server.Server;

public class XiaoYing {
    private static Server server;

    public static Server getServer() {
        return server;
    }

    public static void setServer(Server server) {
        if (XiaoYing.server != null) {
            throw new UnsupportedOperationException("Cannot redefine singleton Server");
        } else {
            XiaoYing.server = server;
//            server.getLogger().info("This server is running " + getName() + " version " + getVersion() + " (Implementing API version " + getBukkitVersion() + ")");
        }
    }

    public static PluginManager getPluginManager() {
        return server.getPluginManager();
    }
}
