package com.habittracker.models;

public class User {
    private int id;
    private String username;
    private String password;

    // 🆕 Gamification fields
    private int xp = 0;
    private int gold = 0;
    private int level = 1;

    // Default Constructor
    public User() {}

    // Parameterized Constructor
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }

    // 🆕 Gamification getters
    public int getXp() { return xp; }
    public int getGold() { return gold; }
    public int getLevel() { return level; }

    // Avoid direct access to the password
    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

    // 🆕 Gamification setters
    public void setXp(int xp) { this.xp = xp; }
    public void setGold(int gold) { this.gold = gold; }
    public void setLevel(int level) { this.level = level; }

    // Override toString() (Excludes password for security)
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", xp=" + xp +
                ", gold=" + gold +
                ", level=" + level +
                '}';
    }
}
