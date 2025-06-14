package com.habittracker.ui;

import com.habittracker.services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backButton;

    public RegisterUI() {
        setTitle("User Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        // Initialize UI components
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        registerButton = new JButton("Register");
        backButton = new JButton("Back to Login");

        // Add components to the layout
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(registerButton);
        add(backButton);

        // Register button click listener
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        // Back button click listener (return to login screen)
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close registration window
                new LoginUI().setVisible(true); // Show the login screen again
            }
        });

        // Set the window visible
        setVisible(true);
    }

    // Method to handle user registration
    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Check if username or password is empty
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.");
            return;
        }

        // Debugging: Log the attempt to register
        System.out.println("Attempting to register user: " + username);

        // Call UserService to register the user
        if (UserService.registerUser(username, password)) {
            // Registration successful
            JOptionPane.showMessageDialog(this, "Registration successful!");
            dispose(); // Close registration window
            new LoginUI().setVisible(true); // Show login screen
        } else {
            // Registration failed (username already taken or error)
            JOptionPane.showMessageDialog(this, "Registration failed. Username may be taken.");
        }
    }
}
