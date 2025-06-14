package com.habittracker.ui;

import com.habittracker.db.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class RewardUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private final String username;
    private JTable rewardsTable;
    private JButton claimButton;
    private JLabel balanceLabel;

    public RewardUI(String username) {
        this.username = username;
        setTitle("Rewards - " + username);
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Balance Panel
        JPanel balancePanel = new JPanel();
        balanceLabel = new JLabel("Loading...");
        balancePanel.add(balanceLabel);
        add(balancePanel, BorderLayout.NORTH);

        // Rewards Table
        rewardsTable = createRewardsTable();
        JScrollPane scrollPane = new JScrollPane(rewardsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Claim Button
        claimButton = new JButton("Claim Selected Reward");
        claimButton.addActionListener(e -> claimReward());
        add(claimButton, BorderLayout.SOUTH);

        // Load data
        refreshBalance();
        setVisible(true);
    }

    private JTable createRewardsTable() {
        String[] columnNames = {"Reward", "XP Cost", "Gold Cost"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        loadRewardsData(model);
        return table;
    }

    private void loadRewardsData(DefaultTableModel model) {
        model.setRowCount(0);
        String query = "SELECT r.reward_name, r.xp_cost, r.gold_cost " +
                "FROM rewards r JOIN users u ON r.user_id = u.id WHERE u.username = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String rewardName = rs.getString("reward_name");
                int xpCost = rs.getInt("xp_cost");
                int goldCost = rs.getInt("gold_cost");
                model.addRow(new Object[]{rewardName, xpCost, goldCost});
            }
        } catch (SQLException e) {
            System.err.println("Error loading rewards: " + e.getMessage());
        }
    }

    private void refreshBalance() {
        String query = "SELECT xp, gold FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int xp = rs.getInt("xp");
                int gold = rs.getInt("gold");
                balanceLabel.setText("XP: " + xp + " | Gold: " + gold);
            }
        } catch (SQLException e) {
            System.err.println("Error loading balance: " + e.getMessage());
        }
    }

    private void claimReward() {
        int selectedRow = rewardsTable.getSelectedRow();
        if (selectedRow >= 0) {
            String rewardName = (String) rewardsTable.getValueAt(selectedRow, 0);
            int xpCost = (int) rewardsTable.getValueAt(selectedRow, 1);
            int goldCost = (int) rewardsTable.getValueAt(selectedRow, 2);

            String query = "SELECT xp, gold FROM users WHERE username = ?";
            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int userXp = rs.getInt("xp");
                    int userGold = rs.getInt("gold");

                    if (userXp >= xpCost && userGold >= goldCost) {
                        claimAndDeduct(xpCost, goldCost);
                    } else {
                        JOptionPane.showMessageDialog(this, "Not enough XP or Gold.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error checking balance: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a reward to claim.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void claimAndDeduct(int xpCost, int goldCost) {
        String updateQuery = "UPDATE users SET xp = xp - ?, gold = gold - ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setInt(1, xpCost);
            stmt.setInt(2, goldCost);
            stmt.setString(3, username);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Reward claimed successfully!");

            // Refresh balance after claiming
            refreshBalance();
        } catch (SQLException e) {
            System.err.println("Error deducting XP/Gold: " + e.getMessage());
        }
    }
}
