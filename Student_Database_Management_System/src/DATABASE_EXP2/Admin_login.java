package DATABASE_EXP2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Admin_login {
	            private JFrame frame;
	            private Connection connection;
	            private JTextField usernameField;
	            private JPasswordField passwordField;

	            public Admin_login() {
	                new Main();
	                adminLoginGUI();
	                //insertAdminCredentials();
	                connectToDatabase();
	            }

	            private void adminLoginGUI() {
	                frame = new JFrame("Admin Login");
	                frame.setBounds(100, 300, 400, 200);
	                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                frame.setLocationRelativeTo(null);
	                frame.getContentPane().setLayout(null);

	                JLabel lblUsername = new JLabel("Username:");
	                lblUsername.setBounds(30, 50, 100, 20);
	                frame.getContentPane().add(lblUsername);

	                usernameField = new JTextField();
	                usernameField.setBounds(140, 50, 150, 20);
	                frame.getContentPane().add(usernameField);
	                usernameField.setColumns(10);

	                JLabel lblPassword = new JLabel("Password:");
	                lblPassword.setBounds(30, 80, 100, 20);
	                frame.getContentPane().add(lblPassword);

	                passwordField = new JPasswordField();
	                passwordField.setBounds(140, 80, 150, 20);
	                frame.getContentPane().add(passwordField);

	                JButton loginButton = new JButton("Login");
	                loginButton.setBounds(30, 120, 100, 30);
	                frame.getContentPane().add(loginButton);

	                JButton backButton = new JButton("Back");
	                backButton.setBounds(140, 120, 100, 30);
	                frame.getContentPane().add(backButton);

	                loginButton.addActionListener(new ActionListener() {
	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                        String username = usernameField.getText();
	                        String password = new String(passwordField.getPassword());

	                        if (authenticateAdmin(username, password)) {
	                            showAdminInterface();
	                        } else {
	                            JOptionPane.showMessageDialog(frame, "Invalid username or password.");
	                        }
	                    }
	                });

	                backButton.addActionListener(new ActionListener() {
	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                        frame.dispose();
	                        Login login = new Login();
	                    }
	                });
	                
	                frame.setVisible(true);
	            }

	            private boolean authenticateAdmin(String username, String password) {
	                try {
	                    PreparedStatement statement = connection.prepareStatement("SELECT * FROM admin WHERE AdminId = ? AND Adminpasword = ?");
	                    statement.setString(1, username);
	                    statement.setString(2, password);
	                    ResultSet resultSet = statement.executeQuery();

	                    return resultSet.next(); // If a row is returned, authentication is successful
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }

	                return false;
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

	            private void showAdminInterface() {
	                frame.dispose(); // Close the login frame
	                Admin admin = new Admin();
	            }

	    
	        }
	    
