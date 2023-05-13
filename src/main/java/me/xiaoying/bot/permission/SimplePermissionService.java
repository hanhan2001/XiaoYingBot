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
    public boolean isOperator(User user) {
        return false;
    }

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
    public void setPermission(User user, String permission) {

    }

    @Override
    public void setPermission(Group group, User user, String permission) {

    }

    @Override
    public void setPermission(Group group, String permission) {

    }

    @Override
    public void unsetPermission(User user, String permission) {

    }

    @Override
    public void unsetPermission(Group group, User user, String permission) {

    }

    @Override
    public void unsetPermission(Group group, String permission) {

    }
}