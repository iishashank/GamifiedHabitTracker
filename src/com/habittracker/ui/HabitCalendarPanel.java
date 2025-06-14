package com.habittracker.ui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class HabitCalendarPanel extends JPanel {
    private final YearMonth month;
    private final Set<LocalDate> completedDays;
    private final int longestStreak;
    private final Color headerColor = new Color(60, 90, 153);
    private final Color backgroundColor = new Color(240, 245, 255);
    private final Color completedDayColor = new Color(76, 175, 80);
    private final Color todayColor = new Color(255, 152, 0);
    private final Color gridColor = new Color(200, 210, 230);
    private final Color textColor = new Color(50, 50, 50);
    private final Color streakColor = new Color(255, 87, 34);
    private final Font headerFont = new Font("Segoe UI", Font.BOLD, 16);
    private final Font dayHeaderFont = new Font("Segoe UI", Font.BOLD, 12);
    private final Font dayFont = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font streakFont = new Font("Segoe UI", Font.BOLD, 13);

    public HabitCalendarPanel(YearMonth month, Set<LocalDate> completedDays, int longestStreak) {
        this.month = month;
        this.completedDays = completedDays;
        this.longestStreak = longestStreak;
        setPreferredSize(new Dimension(400, 300));
        setBackground(backgroundColor);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setToolTipText("Longest streak: " + longestStreak + " days");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        int width = getWidth();
        int height = getHeight();
        int cellSize = Math.min((width - 60) / 7, (height - 120) / 6);
        int startX = (width - (cellSize * 7)) / 2;
        int startY = 70;
        
        // Draw header with month and year
        drawHeader(g2d, width);
        
        // Draw streak information
        drawStreakInfo(g2d, width);
        
        // Draw day headers
        drawDayHeaders(g2d, startX, startY, cellSize);
        
        // Draw calendar days
        drawCalendarDays(g2d, startX, startY, cellSize);
    }
    
    private void drawHeader(Graphics2D g2d, int width) {
        g2d.setColor(headerColor);
        g2d.setFont(headerFont);
        String monthYear = month.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(monthYear);
        g2d.drawString(monthYear, (width - textWidth) / 2, 30);
    }
    
    private void drawStreakInfo(Graphics2D g2d, int width) {
        g2d.setFont(streakFont);
        String streakText = "Longest Streak: " + longestStreak + " days";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(streakText);
        
        // Draw streak badge
        g2d.setColor(streakColor);
        int badgeWidth = textWidth + 20;
        int badgeHeight = 24;
        int badgeX = (width - badgeWidth) / 2;
        int badgeY = 40;
        g2d.fillRoundRect(badgeX, badgeY, badgeWidth, badgeHeight, 12, 12);
        
        // Draw streak text
        g2d.setColor(Color.WHITE);
        g2d.drawString(streakText, badgeX + 10, badgeY + 17);
    }
    
    private void drawDayHeaders(Graphics2D g2d, int startX, int startY, int cellSize) {
        g2d.setFont(dayHeaderFont);
        g2d.setColor(headerColor);
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < 7; i++) {
            int x = startX + i * cellSize;
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(days[i]);
            g2d.drawString(days[i], x + (cellSize - textWidth) / 2, startY - 10);
        }
    }
    
    private void drawCalendarDays(Graphics2D g2d, int startX, int startY, int cellSize) {
        LocalDate firstDay = month.atDay(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue() % 7;
        int daysInMonth = month.lengthOfMonth();
        LocalDate today = LocalDate.now();
        
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = month.atDay(day);
            int column = (dayOfWeek + day - 1) % 7;
            int row = (dayOfWeek + day - 1) / 7;
            
            int x = startX + column * cellSize;
            int y = startY + row * cellSize;
            
            boolean isCompleted = completedDays.contains(date);
            boolean isToday = date.equals(today);
            
            // Draw day cell
            drawDayCell(g2d, x, y, cellSize, day, isCompleted, isToday);
        }
    }
    
    private void drawDayCell(Graphics2D g2d, int x, int y, int cellSize, int day, boolean isCompleted, boolean isToday) {
        // Draw cell background
        if (isToday) {
            g2d.setColor(todayColor.brighter());
            g2d.fillRoundRect(x + 2, y + 2, cellSize - 4, cellSize - 4, 10, 10);
            g2d.setColor(todayColor);
            g2d.setStroke(new BasicStroke(2f));
            g2d.drawRoundRect(x + 2, y + 2, cellSize - 4, cellSize - 4, 10, 10);
        } else if (isCompleted) {
            // Create gradient for completed days
            GradientPaint gradient = new GradientPaint(
                x, y, completedDayColor,
                x, y + cellSize, completedDayColor.darker()
            );
            g2d.setPaint(gradient);
            g2d.fillRoundRect(x + 2, y + 2, cellSize - 4, cellSize - 4, 10, 10);
        } else {
            // Regular day cell
            g2d.setColor(Color.WHITE);
            g2d.fillRoundRect(x + 2, y + 2, cellSize - 4, cellSize - 4, 10, 10);
            g2d.setColor(gridColor);
            g2d.drawRoundRect(x + 2, y + 2, cellSize - 4, cellSize - 4, 10, 10);
        }
        
        // Draw day number
        g2d.setFont(dayFont);
        if (isCompleted || isToday) {
            g2d.setColor(Color.WHITE);
        } else {
            g2d.setColor(textColor);
        }
        
        String dayText = String.valueOf(day);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(dayText);
        int textHeight = fm.getHeight();
        g2d.drawString(dayText, x + (cellSize - textWidth) / 2, y + (cellSize + textHeight) / 2 - 2);
        
        // Draw completion indicator
        if (isCompleted && !isToday) {
            g2d.setColor(Color.WHITE);
            g2d.fillOval(x + cellSize - 15, y + 8, 8, 8);
        }
    }
}