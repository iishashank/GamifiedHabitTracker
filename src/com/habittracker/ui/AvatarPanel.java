package com.habittracker.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class AvatarPanel extends JPanel {
    private BufferedImage avatarImage;

    public AvatarPanel() {
        setPreferredSize(new Dimension(80, 80));
        setOpaque(false);
    }

    public void setAvatar(File file) {
        try {
            avatarImage = ImageIO.read(file);
            repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load image.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (avatarImage != null) {
            // Draw circular avatar
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, 80, 80));
            g2.drawImage(avatarImage, 0, 0, 80, 80, this);
            g2.dispose();
        }
    }
}