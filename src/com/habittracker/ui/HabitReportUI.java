package com.habittracker.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.habittracker.services.HabitService;

public class HabitReportUI extends JFrame {
    private String username;

    public HabitReportUI(String username) {
        this.username = username;
        setTitle("Habit Report");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Habit Progress Report", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel, BorderLayout.NORTH);

        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(reportArea);
        add(scrollPane, BorderLayout.CENTER);

        List<String> progressList = HabitService.getHabitProgress(username);
        if (progressList.isEmpty()) {
            reportArea.setText("No habits found for user: " + username);
        } else {
            for (String progress : progressList) {
                reportArea.append(progress + "\n");
            }
        }

        setVisible(true);
    }
}
