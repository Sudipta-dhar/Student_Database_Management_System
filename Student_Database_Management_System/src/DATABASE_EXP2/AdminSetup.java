package DATABASE_EXP2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminSetup {
    private JFrame frame;
    private Connection connection;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public AdminSetup() {
        setupGUI();
        connectToDatabase();
    }

    private void setupGUI() {
        frame = new JFrame("Admin Setup");
        frame.setBounds(100, 300, 400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(null);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(30, 20, 100, 20);
        frame.getContentPane().add(lblUsername);

        usernameField = new JTextField();
        usernameField.setBounds(140, 20, 200, 20);
        frame.getContentPane().add(usernameField);
        usernameField.setColumns(10);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(30, 50, 100, 20);
        frame.getContentPane().add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(140, 50, 200, 20);
        frame.getContentPane().add(passwordField);

        JButton setupButton = new JButton("Setup");
        setupButton.setBounds(140, 90, 100, 30);
        frame.getContentPane().add(setupButton);

        setupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String adminId = usernameField.getText();
                String adminPassword = new String(passwordField.getPassword());

                if (adminId.isEmpty() || adminPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter admin ID and password.");
                } else {
                    addAdminCredentials(adminId, adminPassword);
                }
            }
        });
        JButton backButton = new JButton("Back to MainMenu");
        backButton.setBounds(115, 145, 150, 30);
        frame.getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               frame.getContentPane().removeAll();
               frame.dispose();
               Admin admin = new Admin();
           }
       });

        frame.setVisible(true);
    }

    private void addAdminCredentials(String adminId, String adminPassword) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO admin (AdminId, Adminpasword) VALUES (?, ?)");
            statement.setString(1, adminId);
            statement.setString(2, adminPassword);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Admin credentials added successfully.");
            frame.dispose();
            Admin admin = new Admin();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while adding admin credentials.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/college_db", "root", "Ssd@#1234567890");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

   
   
    
}