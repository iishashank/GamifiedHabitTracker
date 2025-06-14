package com.habittracker.ui;

import javax.swing.*;
import java.awt.*;

public class DashboardUI extends JFrame {
    
    private JPanel createCardPanel(JButton button, Color bgColor) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(new Color(240, 245, 255));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 10), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        styleButton(button, bgColor);
        cardPanel.add(button, BorderLayout.CENTER);
        
        return cardPanel;
    }
    
    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new RoundedBorder(15, new Color(0, 0, 0, 30)));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 0, 0, 30), 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
            }
        });
    }
    private static final long serialVersionUID = 1L;
    private final String username;
    private HabitProgressUI habitProgressUI = null;

    public DashboardUI(String username) {
        this.username = username;

        setTitle("Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 245, 255));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout(10, 10));
        
        // Add subtle animation effects
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setDefaultLookAndFeelDecorated(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Header Panel
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(70, 130, 180), getWidth(), 0, new Color(100, 149, 237));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JLabel titleLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Main Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        buttonPanel.setBackground(new Color(240, 245, 255));
        buttonPanel.setOpaque(true);
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 10), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        JButton habitButton = new JButton("Manage Habits");
        styleButton(habitButton, new Color(70, 130, 180));
        JButton progressButton = new JButton("Manage Progress");
        styleButton(progressButton, new Color(100, 149, 237));
        JButton reportButton = new JButton("View Habit Report");
        styleButton(reportButton, new Color(65, 105, 225));
        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton, new Color(220, 20, 60));

        buttonPanel.add(createCardPanel(habitButton, new Color(70, 130, 180)));
        buttonPanel.add(createCardPanel(progressButton, new Color(100, 149, 237)));
        buttonPanel.add(createCardPanel(reportButton, new Color(65, 105, 225)));
        buttonPanel.add(createCardPanel(logoutButton, new Color(220, 20, 60)));
        add(buttonPanel, BorderLayout.CENTER);
        
        // Status Bar
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(new Color(240, 245, 255));
        statusPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0, 0, 0, 10)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        JLabel statusLabel = new JLabel("Logged in as: " + username + " | Current Streak: 7 days | Total Habits: 5");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(100, 100, 100));
        statusPanel.add(statusLabel);
        add(statusPanel, BorderLayout.SOUTH);

        habitButton.addActionListener(e -> new HabitUI(username));

        progressButton.addActionListener(e -> {
            if (habitProgressUI == null || !habitProgressUI.isDisplayable()) {
                habitProgressUI = new HabitProgressUI(username);
                habitProgressUI.setVisible(true);
            } else {
                habitProgressUI.toFront();
            }
        });
        
        reportButton.addActionListener(e -> new HabitReportUI(username));

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginUI();
        });

        setOpacity(0);
        setVisible(true);
        
        // Fade-in animation
        Timer timer = new Timer(10, e -> {
            float opacity = getOpacity();
            if (opacity < 1) {
                setOpacity(opacity + 0.05f);
            } else {
                ((Timer)e.getSource()).stop();
            }
        });
        timer.start();
    }
}