package com.example.p2_declutter_app;

public class Achievement {
    private String id;
    private String name;
    private String description;
    private boolean unlocked;

    public Achievement(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unlocked = false;
    }

    // Getters and setters
    public String getId() { return id; }
    public boolean isUnlocked() { return unlocked; }
    public void unlock() { this.unlocked = true; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}
