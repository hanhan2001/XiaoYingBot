package me.xiaoying.bot.configuration.serialization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface DelegateDeserialization {
  Class<? extends ConfigurationSerializable> value();
}


/* Location:              F:\我的世界\服务器\服务器核心\[Spigot] 1.8.8(java8).jar!\org\bukkit\configuration\serialization\DelegateDeserialization.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */