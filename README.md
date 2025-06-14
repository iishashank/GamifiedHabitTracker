# Gamified Habit Tracker

## Project Overview

The Gamified Habit Tracker is a desktop-based application built to help users develop and maintain good habits through gamification principles. The system encourages consistency by rewarding users with points and levels for completing their daily tasks. It is designed as a productivity tool for students, professionals, and anyone interested in habit-building.

## Features

* Allows users to track multiple habits simultaneously.
* Gamified system with XP, levels, and rewards.
* Calendar view for habit logs and streak tracking.
* User registration and login with secure password encryption.
* Dashboard displaying progress metrics and habit statistics.
* Database integration for persistent storage.
* Backup and restore functionality for user data.

## Technologies Used

* Java (Core, OOP)
* Java Swing for the graphical user interface
* MySQL for backend database management
* JDBC for database connectivity
* SHA-256 encryption for secure password storage

## Project Structure

```
GamifiedHabitTracker/
├── src/
│   ├── com/habittracker/ui/        // GUI components and screens
│   ├── com/habittracker/models/    // Data models for habits and users
│   ├── com/habittracker/services/  // Logic for XP, rewards, and tracking
│   └── com/habittracker/db/        // Database connection and queries
├── assets/                         // Icons, images, and other resources
├── db/                             // SQL scripts and schema dump
├── bin/                            // Compiled .class files
├── README.md
└── setup.sql
```

## Setup and Installation

1. Clone the repository:

   ```
   git clone https://github.com/iishashank/GamifiedHabitTracker.git
   cd GamifiedHabitTracker
   ```

2. Set up the MySQL database:

   * Open MySQL and import `setup.sql` to create the required schema.
   * Update the database configuration in `DatabaseConnection.java` with your local credentials.

3. Compile and run the application:

   ```
   javac -cp ".:mysql-connector-j-9.2.0.jar" src/com/habittracker/ui/Main.java
   java -cp ".:mysql-connector-j-9.2.0.jar" com.habittracker.ui.Main
   ```

## Future Improvements

* Add mobile application support (Android)
* Integrate Firebase or cloud-based data sync
* Add push notifications and reminders
* Implement graphical data visualization for user analytics
* Add community feature to share and compete on habit goals

## Author

Shashank Rallabandi

* Email: [shashankrallabandi@example.com](mailto:shashankrallabandi@example.com)
* LinkedIn: [https://linkedin.com/in/iishashank](https://linkedin.com/in/iishashank)
* Portfolio: [https://iishashank.github.io](https://iishashank.github.io)

## License

This project is licensed under the MIT License.
