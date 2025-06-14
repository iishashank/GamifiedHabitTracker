package com.habittracker.ui;

import com.habittracker.services.HabitService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HabitUI extends JFrame {
    private String username;
    private JTextField habitField;
    private JButton addButton, deleteButton;
    private JList<String> habitList;
    private DefaultListModel<String> listModel;

    public HabitUI(String username) {
        this.username = username;
        setTitle("Habit Manager");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        habitList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(habitList);

        habitField = new JTextField();
        addButton = new JButton("Add Habit");
        deleteButton = new JButton("Delete Habit");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(habitField);
        panel.add(addButton);
        panel.add(deleteButton);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        loadHabits();

        addButton.addActionListener(e -> addHabit());
        deleteButton.addActionListener(e -> deleteHabit());

        setVisible(true);
    }

    private void loadHabits() {
        listModel.clear();
        List<String> habits = HabitService.getUserHabits(username);
        for (String habit : habits) {
            listModel.addElement(habit);
        }
    }

    private void addHabit() {
        String habitName = habitField.getText();
        if (!habitName.isEmpty()) {
            if (HabitService.addHabit(username, habitName)) {
                listModel.addElement(habitName);
                habitField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add habit.");
            }
        }
    }

    private void deleteHabit() {
        String selectedHabit = habitList.getSelectedValue();
        if (selectedHabit != null) {
            if (HabitService.deleteHabit(username, selectedHabit)) {
                listModel.removeElement(selectedHabit);
                habitField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete habit.");
            }
        }
    }
}
