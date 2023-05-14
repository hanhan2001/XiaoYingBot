package me.xiaoying.bot.permission;

import me.xiaoying.bot.entity.Group;
import me.xiaoying.bot.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimplePermissionService implements PermissionService {
    Map<User, Set<Permission>> userPermissions = new HashMap<>();
    Map<Group, Set<Permission>> groupPermissions = new HashMap<>();

    @Override
    public boolean isAdmin(User user) {
        return false;
    }

    @Override
    public boolean hasPermission(User user, String permission) {
        return false;
    }

    public boolean isAdmin(Group group, User user) {
        return false;
    }

    @Override
    public boolean hasPermission(Group group, User user, String permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Group group, String permission) {
        return false;
    }

    @Override
    public boolean setPermission(User user, String permission) {
        return false;
    }

    @Override
    public boolean setPermission(String group, String permission) {
        return false;
    }

    @Override
    public boolean setPermission(Group group, User user, String permission) {
        return false;
    }

    @Override
    public boolean setPermission(Group group, String ggroup, String permission) {
        return false;
    }

    @Override
    public boolean unsetPermission(User user, String permission) {
        return false;
    }

    @Override
    public boolean unsetPermission(String group, String permission) {
        return false;
    }

    @Override
    public boolean unsetPermission(Group group, User user, String permission) {
        return false;
    }

    @Override
    public boolean unsetPermission(Group group, String ggroup, String permission) {
        return false;
    }
}