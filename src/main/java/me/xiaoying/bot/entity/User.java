package me.xiaoying.bot.entity;

public class User {
    long id;
    String name;

    public User(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}