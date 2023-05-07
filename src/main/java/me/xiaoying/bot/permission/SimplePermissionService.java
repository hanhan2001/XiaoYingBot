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

    @Override
    public boolean hasPermission(Group user, String permission) {

        return false;
    }

    @Override
    public void setPermission(User user, String permission) {

    }

    @Override
    public void setPermission(Group group, String permission) {

    }

    @Override
    public void removePermission(User user, String permission) {

    }

    @Override
    public void removePermission(Group group, String permission) {

    }
}