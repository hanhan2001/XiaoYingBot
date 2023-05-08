package me.xiaoying.bot.permission;

import me.xiaoying.bot.entity.Group;
import me.xiaoying.bot.entity.User;

/**
 * 接口 权限
 */
public interface PermissionService {
     boolean isAdmin(User user);

    boolean hasPermission(User user, String permission);
    boolean hasPermission(Group group, String permission);

    void setPermission(User user, String permission);
    void setPermission(Group group, String permission);

    void unsetPermission(User user, String permission);
    void unsetPermission(Group group, String permission);
}