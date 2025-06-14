package com.habittracker.ui;

import com.habittracker.db.DatabaseConnection;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Gamified Habit Tracker Started!");
        DatabaseConnection.connect();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set look and feel.");
        }

        new LoginUI();
    }

    public static void openMainUI(String username) {
        JFrame mainFrame = new JFrame("Gamified Habit Tracker - " + username);
        mainFrame.setSize(500, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.getContentPane().setBackground(new Color(250, 250, 255));

        // Add Profile menu with Switch User
        JMenuBar menuBar = new JMenuBar();
        JMenu profileMenu = new JMenu("Profile");
        JMenuItem switchUserItem = new JMenuItem("Switch User");
        switchUserItem.addActionListener(e -> {
            mainFrame.dispose();
            new LoginUI();
        });
        profileMenu.add(switchUserItem);
        
        // Removed Analytics menu
        
        menuBar.add(profileMenu);
        mainFrame.setJMenuBar(menuBar);

        JButton rewardsButton = new JButton("View Rewards");
        rewardsButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rewardsButton.setBackground(new Color(100, 149, 237));
        rewardsButton.setForeground(Color.WHITE);
        rewardsButton.setFocusPainted(false);
        rewardsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        rewardsButton.addActionListener(e -> new RewardUI(username));
        
        // Removed Analytics button
        
        mainFrame.add(rewardsButton);
        
        // Example data for demonstration
        java.time.YearMonth currentMonth = java.time.YearMonth.now();
        java.util.Set<java.time.LocalDate> completedDays = java.util.Set.of(
            java.time.LocalDate.now().minusDays(2),
            java.time.LocalDate.now().minusDays(1),
            java.time.LocalDate.now()
        );
        int longestStreak = 3; // Replace with real calculation

        HabitCalendarPanel calendarPanel = new HabitCalendarPanel(currentMonth, completedDays, longestStreak);
        mainFrame.add(calendarPanel);
        
        mainFrame.setVisible(true);
    }
}
