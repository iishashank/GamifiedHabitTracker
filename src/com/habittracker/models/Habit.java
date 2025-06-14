package com.habittracker.models;

public class Habit {
    private int id;
    private String name;
    private String difficulty;
    private String frequency;

    // Default Constructor
    public Habit() {}

    // Parameterized Constructor
    public Habit(int id, String name, String difficulty, String frequency) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
        this.frequency = frequency;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDifficulty() { return difficulty; }
    public String getFrequency() { return frequency; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public void setFrequency(String frequency) { this.frequency = frequency; }

    // Override toString() for better debugging
    @Override
    public String toString() {
        return "Habit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", frequency='" + frequency + '\'' +
                '}';
    }
}
