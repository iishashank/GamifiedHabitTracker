package com.habittracker.services;

import com.habittracker.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HabitService {

    // Method to get all habits for a specific user
    public static List<String> getUserHabits(String username) {
        List<String> habits = new ArrayList<>();
        String query = "SELECT h.habit_name FROM habits h " +
                       "JOIN users u ON h.user_id = u.id WHERE u.username = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                habits.add(rs.getString("habit_name"));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching habits: " + e.getMessage());
        }
        return habits;
    }

    // Method to update habit progress (completed count and streak) and grant XP and Gold
    public static boolean updateHabitProgress(String username, String habitName) {
        String updateHabitQuery = "UPDATE habits SET completed_count = completed_count + 1, streak = streak + 1 " +
                                  "WHERE user_id = (SELECT id FROM users WHERE username = ?) AND habit_name = ?";
        String updateXPQuery = "UPDATE users SET xp = xp + 10 WHERE username = ?";
        String updateGoldQuery = "UPDATE users SET gold = gold + 5 WHERE username = ?";

        try (Connection conn = DatabaseConnection.connect()) {
            conn.setAutoCommit(false); // Disable auto-commit

            try (PreparedStatement stmt1 = conn.prepareStatement(updateHabitQuery);
                 PreparedStatement stmt2 = conn.prepareStatement(updateXPQuery);
                 PreparedStatement stmt3 = conn.prepareStatement(updateGoldQuery)) {

                stmt1.setString(1, username);
                stmt1.setString(2, habitName);
                stmt2.setString(1, username);
                stmt3.setString(1, username);

                int r1 = stmt1.executeUpdate();
                int r2 = stmt2.executeUpdate();
                int r3 = stmt3.executeUpdate();

                if (r1 > 0 && r2 > 0 && r3 > 0) {
                    conn.commit(); // Commit transaction
                    return true;
                } else {
                    conn.rollback(); // Rollback transaction on failure
                }

            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction in case of error
                System.err.println("Error during update: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }

        return false;
    }

    // Method to get the total XP of a user
    public static int getTotalXP(String username) {
        String query = "SELECT xp FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("xp");
            }

        } catch (SQLException e) {
            System.err.println("Error fetching XP: " + e.getMessage());
        }
        return 0;
    }

    // Method to get the total Gold of a user
    public static int getTotalGold(String username) {
        String query = "SELECT gold FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("gold");
            }

        } catch (SQLException e) {
            System.err.println("Error fetching gold: " + e.getMessage());
        }
        return 0;
    }

    // Method to get habit stats (completed count and streak) for a user
    public static List<String[]> getHabitStats(String username) {
        List<String[]> stats = new ArrayList<>();
        String query = "SELECT h.habit_name, h.completed_count, h.streak FROM habits h " +
                       "JOIN users u ON h.user_id = u.id WHERE u.username = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String[] row = {
                    rs.getString("habit_name"),
                    String.valueOf(rs.getInt("completed_count")),
                    String.valueOf(rs.getInt("streak"))
                };
                stats.add(row);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching habit stats: " + e.getMessage());
        }

        return stats;
    }

    // Method to add a new habit for a user
    public static boolean addHabit(String username, String habitName) {
        String query = "INSERT INTO habits (user_id, habit_name) VALUES ((SELECT id FROM users WHERE username = ?), ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, habitName);

            int result = stmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.err.println("Error adding habit: " + e.getMessage());
        }

        return false;
    }

    // Method to get habit progress for a user (returns a list of progress details)
    public static List<String> getHabitProgress(String username) {
        List<String> progressList = new ArrayList<>();
        String query = "SELECT h.habit_name, h.completed_count, h.streak FROM habits h " +
                       "JOIN users u ON h.user_id = u.id WHERE u.username = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String progress = "Habit: " + rs.getString("habit_name") +
                                  ", Completed: " + rs.getInt("completed_count") +
                                  ", Streak: " + rs.getInt("streak");
                progressList.add(progress);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching habit progress: " + e.getMessage());
        }

        return progressList;
    }

    // Method to delete a habit for a user
    public static boolean deleteHabit(String username, String habitName) {
        String query = "DELETE FROM habits WHERE user_id = (SELECT id FROM users WHERE username = ?) AND habit_name = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, habitName);

            int result = stmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting habit: " + e.getMessage());
        }

        return false;
    }
}
