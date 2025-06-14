package com.habittracker.ui;

import com.habittracker.services.HabitService;
import com.habittracker.services.RewardService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProgressRewardsUI extends JFrame {
    private String username;
    
    public ProgressRewardsUI(String username) {
        this.username = username;
        setTitle("Progress & Rewards");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Habit Progress & Rewards", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2, 1));

        // Habit Progress Section
        JPanel habitPanel = new JPanel();
        habitPanel.setBorder(BorderFactory.createTitledBorder("Your Habit Progress"));
        JTextArea habitProgressArea = new JTextArea(5, 30);
        habitProgressArea.setEditable(false);
        habitPanel.add(new JScrollPane(habitProgressArea));
        contentPanel.add(habitPanel);

        // Rewards Section
        JPanel rewardPanel = new JPanel();
        rewardPanel.setBorder(BorderFactory.createTitledBorder("Your Rewards"));
        JTextArea rewardArea = new JTextArea(5, 30);
        rewardArea.setEditable(false);
        rewardPanel.add(new JScrollPane(rewardArea));
        contentPanel.add(rewardPanel);

        add(contentPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Dashboard");
        add(backButton, BorderLayout.SOUTH);

        // Load data
        loadProgress(habitProgressArea);
        loadRewards(rewardArea);

        backButton.addActionListener(e -> dispose());  // Close window

        setVisible(true);
    }

    private void loadProgress(JTextArea habitProgressArea) {
        List<String> progressList = HabitService.getHabitProgress(username);
        StringBuilder progressText = new StringBuilder();
        for (String progress : progressList) {
            progressText.append(progress).append("\n");
        }
        habitProgressArea.setText(progressText.toString());
    }

    private void loadRewards(JTextArea rewardArea) {
        List<String> rewardsList = RewardService.getUserRewards(username);
        StringBuilder rewardText = new StringBuilder();
        for (String reward : rewardsList) {
            rewardText.append(reward).append("\n");
        }
        rewardArea.setText(rewardText.toString());
    }
}
