package com.examplee.radu.getrich.User;

public class User {
    public String name;
    public int value;
    public String level;
    public String location;
    public String description;

    public User() {
        this.name = "Nume";
        this.value = 0;
        this.level = "sarac";
        this.location = "Bucuresti";
        this.description = "Schimba descrierea";
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String getLevel() {
        return level;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }
}
