package me.xiaoying.bot.permission;

/**
 * 实体 权限
 */
public class Permission {
    String permission;
    String description;

    public Permission(String permission) {
        this.permission = permission;
    }

    public Permission(String permission, String description) {
        this.permission = permission;
        this.description = description;
    }

    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }
}