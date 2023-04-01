package me.xiaoying.bot.cache;

import me.xiaoying.bot.plugin.Plugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Caches {
    public static Map<String, Plugin> plugins = new HashMap<>();
    public static Set<Thread> threadSet = new HashSet<>();
    public static Map<String, Thread> threadCaches = new HashMap<>();
}