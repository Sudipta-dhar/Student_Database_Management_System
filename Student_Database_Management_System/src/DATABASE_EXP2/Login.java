package DATABASE_EXP2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton adminButton;
    private JButton registrationButton;

    private Connection connection;

    public Login() {
        initialize();
        connectToDatabase();
    }

    private void initialize() {
        setTitle("Login");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create gradient panel with custom colors
        GradientPanel gradientPanel = new GradientPanel(new Color(70, 130, 100), new Color(75, 100, 210), new Color(250, 255, 60));
        gradientPanel.setLayout(null);
        add(gradientPanel);

        // Create login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setBounds(50, 50, 400, 150);
        loginPanel.setBackground(new Color(0, 0, 0, 0));
        loginPanel.setLayout(new GridLayout(3, 3, 10, 10));
        gradientPanel.add(loginPanel);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        loginButton.setBounds(20, 370, 150, 30);
       
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (validateLogin(username, password)) {
                    // Redirect to student or teacher information page based on user type
                    if (isStudent(username)) {
                        // Show student information
                        StudentInfoFrame studentInfoFrame = new StudentInfoFrame(connection, username);
                        studentInfoFrame.setVisible(true);
                    } else if (isTeacher(username)) {
                        // Show teacher information
                        TeacherInfoFrame teacherInfoFrame = new TeacherInfoFrame(connection, username);
                        teacherInfoFrame.setVisible(true);
                    }

                    // Close the login frame
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBounds(50, 200, 400, 50);
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridLayout(1, 3, 10, 2));
        gradientPanel.add(buttonsPanel);

        adminButton = new JButton("Admin Login");
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Admin_login login1 = new Admin_login();
            	dispose();
            }
        });
        buttonsPanel.add(adminButton);

        registrationButton = new JButton("Registration");
        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Registration registration = new Registration();
            	dispose();  
            }
        });
        buttonsPanel.add(registrationButton);

        setVisible(true);
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/college_db", "root", "Ssd@#1234567890");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private boolean validateLogin(String username, String password) {
        try {
            String query = "SELECT * FROM stregistration WHERE STusername = ? AND Password = ? UNION SELECT * FROM tregistration WHERE Tusername = ? AND Password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, username);
            statement.setString(4, password);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while validating login.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean isStudent(String username) {
        try {
            String query = "SELECT * FROM stregistration WHERE STusername = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while checking user type.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private boolean isTeacher(String username) {
        try {
            String query = "SELECT * FROM tregistration WHERE Tusername = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while checking user type.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}

