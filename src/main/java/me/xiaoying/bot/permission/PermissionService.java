package me.xiaoying.bot.permission;

import me.xiaoying.bot.entity.Group;
import me.xiaoying.bot.entity.User;
/**
 * 接口 权限
 */
public interface PermissionService {
    boolean isAdmin(User user);

    boolean isAdmin(Group group, User user);

    boolean hasPermission(User user, String permission);

    boolean hasPermission(Group group, User user, String permission);

    boolean hasPermission(Group group, String permission);

    boolean setPermission(User user, String permission);

    boolean setPermission(Group group, User user, String permission);

    boolean setPermission(Group group, String permission);

    boolean unsetPermission(User user, String permission);

    boolean unsetPermission(Group group, User user, String permission);

    boolean unsetPermission(Group group, String permission);
}