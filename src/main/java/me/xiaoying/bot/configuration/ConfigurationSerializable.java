package me.xiaoying.bot.configuration;

import java.util.Map;

public interface ConfigurationSerializable {
    Map<String, Object> serialize();
}
