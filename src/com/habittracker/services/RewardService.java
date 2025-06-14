package com.habittracker.services;

import com.habittracker.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RewardService {

    // ✅ Method to fetch user rewards
    public static List<String> getUserRewards(String username) {
        List<String> rewards = new ArrayList<>();
        String query = "SELECT reward_name FROM rewards WHERE user_id = (SELECT id FROM users WHERE username = ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                rewards.add(rs.getString("reward_name"));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching rewards: " + e.getMessage());
            e.printStackTrace();
        }
        return rewards;
    }

    // 🆕 Method to add XP and gold to the user
    public static void addXpAndGold(String username, int xpEarned, int goldEarned) {
        String selectQuery = "SELECT xp, gold, level FROM users WHERE username = ?";
        String updateQuery = "UPDATE users SET xp = ?, gold = ?, level = ? WHERE username = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {

            selectStmt.setString(1, username);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int xp = rs.getInt("xp");
                int gold = rs.getInt("gold");
                int level = rs.getInt("level");

                xp += xpEarned;
                gold += goldEarned;

                // Level up for every 100 XP
                while (xp >= 100) {
                    xp -= 100;
                    level += 1;
                }

                updateStmt.setInt(1, xp);
                updateStmt.setInt(2, gold);
                updateStmt.setInt(3, level);
                updateStmt.setString(4, username);
                updateStmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Error updating user XP/Gold: " + e.getMessage());
        }
    }
}
