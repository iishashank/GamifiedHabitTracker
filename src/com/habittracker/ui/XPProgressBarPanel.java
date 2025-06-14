package com.habittracker.ui;

import javax.swing.*;
import java.awt.*;

public class XPProgressBarPanel extends JPanel {
    private JProgressBar xpBar;

    public XPProgressBarPanel(int xp, int xpMax) {
        setLayout(new BorderLayout());
        xpBar = new JProgressBar(0, xpMax);
        xpBar.setValue(xp);
        xpBar.setStringPainted(true);
        xpBar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        setXPBarColor(xp, xpMax);
        add(xpBar, BorderLayout.CENTER);
    }

    private void setXPBarColor(int xp, int xpMax) {
        int percent = (int) ((xp * 100.0) / xpMax);
        if (percent < 30) {
            xpBar.setForeground(Color.RED);
        } else if (percent < 70) {
            xpBar.setForeground(Color.ORANGE);
        } else {
            xpBar.setForeground(Color.GREEN.darker());
        }
    }
}