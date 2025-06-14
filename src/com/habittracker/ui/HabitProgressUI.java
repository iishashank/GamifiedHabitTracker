package com.habittracker.ui;

import com.habittracker.services.HabitService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HabitProgressUI extends JFrame {
    private final String username;
    private JComboBox<String> habitDropdown;
    private JButton updateButton;
    private JTable progressTable;
    private JProgressBar xpProgressBar;
    private JLabel levelLabel;
    private JLabel goldLabel;

    private static final Color EMERALD = new Color(0, 184, 148);

    public HabitProgressUI(String username) {
        this.username = username;
        setTitle("Habit Progress Dashboard");
        setSize(650, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(EMERALD);
        setLayout(new BorderLayout());

        initUI();
        updateXPLevel();
        setVisible(true);
    }

    private void initUI() {
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(EMERALD);

        JLabel habitLabel = new JLabel("Select Habit:");
        habitLabel.setForeground(Color.WHITE);
        habitLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        topPanel.add(habitLabel);

        List<String> habits = HabitService.getUserHabits(username);
        System.out.println("Loaded habits for user: " + username + " → " + habits);

        if (habits == null || habits.isEmpty()) {
            habitDropdown = new JComboBox<>(new String[]{"No habits found"});
            habitDropdown.setEnabled(false);

            updateButton = new JButton("Update Progress");
            updateButton.setEnabled(false);

            JOptionPane.showMessageDialog(this, "No habits found for this user.\nPlease add some habits in 'Manage Habits'.", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            habitDropdown = new JComboBox<>(habits.toArray(new String[0]));
            habitDropdown.setPreferredSize(new Dimension(200, 25));

            updateButton = new JButton("Update Progress");
            updateButton.setFocusPainted(false);
            updateButton.setBackground(Color.WHITE);
            updateButton.setForeground(EMERALD);
            updateButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
            updateButton.addActionListener(e -> updateHabit());
        }

        topPanel.add(habitDropdown);
        topPanel.add(updateButton);
        add(topPanel, BorderLayout.NORTH);

        progressTable = createProgressTable();
        add(new JScrollPane(progressTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(3, 1));
        bottomPanel.setBackground(EMERALD);

        levelLabel = new JLabel("Level: 0", JLabel.CENTER);
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bottomPanel.add(levelLabel);

        xpProgressBar = new JProgressBar(0, 100);
        xpProgressBar.setStringPainted(true);
        bottomPanel.add(xpProgressBar);

        goldLabel = new JLabel("Gold: 0", JLabel.CENTER);
        goldLabel.setForeground(Color.WHITE);
        goldLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bottomPanel.add(goldLabel);

        add(bottomPanel, BorderLayout.SOUTH);

        refreshProgressTable();
    }

    private JTable createProgressTable() {
        String[] columns = {"Habit", "Completed Count", "Streak"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < columns.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        return table;
    }

    private void refreshProgressTable() {
        DefaultTableModel model = (DefaultTableModel) progressTable.getModel();
        model.setRowCount(0);
        List<String[]> data = HabitService.getHabitStats(username);
        if (data != null) {
            for (String[] row : data) {
                model.addRow(row);
            }
        }
    }

    private void updateXPLevel() {
        int xp = HabitService.getTotalXP(username);
        int level = xp / 100;
        int currentXP = xp % 100;
        int gold = HabitService.getTotalGold(username);

        levelLabel.setText("Level: " + level);
        xpProgressBar.setValue(currentXP);
        xpProgressBar.setString(currentXP + " / 100 XP");
        goldLabel.setText("Gold: " + gold);
    }

    private void updateHabit() {
        String habit = (String) habitDropdown.getSelectedItem();
        if (habit != null && !habit.equals("No habits found")) {
            boolean updated = HabitService.updateHabitProgress(username, habit);
            if (updated) {
                updateXPLevel();
                refreshProgressTable();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No habit selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
