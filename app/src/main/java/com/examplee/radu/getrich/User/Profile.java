package com.examplee.radu.getrich.User;


public class Profile {
    private String description;
    private String level;
    private String location;
    private String name;
    private long value;

    public Profile() {
    }
    public Profile(String description, String level, String location, String name, long value)
    {
        this.description = description;
        this.level = level;
        this.location = location;
        this.name = name;
        this.value = value;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
