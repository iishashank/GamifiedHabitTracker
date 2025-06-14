package com.habittracker.ui;

import com.habittracker.services.UserService;
import java.util.Scanner;

public class AuthUI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();

        while (true) {
            System.out.println("\nWelcome to Gamified Habit Tracker!");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("❌ Invalid input. Please enter a number.");
                scanner.next(); // Consume invalid input
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    boolean success = userService.registerUser(username, password);
                    System.out.println(success ? "✅ User registered successfully!" : "❌ Registration failed. Try a different username.");
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    String loginUser = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPass = scanner.nextLine();

                    boolean isAuthenticated = userService.authenticateUser(loginUser, loginPass);
                    System.out.println(isAuthenticated ? "✅ Login successful! Welcome, " + loginUser : "❌ Invalid username or password.");
                    break;

                case 3:
                    System.out.println("Exiting application...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("❌ Invalid option. Please choose a valid option.");
            }
        }
    }
}
