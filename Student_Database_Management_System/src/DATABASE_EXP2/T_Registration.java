package DATABASE_EXP2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class T_Registration {
	private JFrame frame;
	  private Connection connection;
    
	public T_Registration() {
		new Main();
		TRegistrationForm();
		connectToDatabase();
	}
    private void TRegistrationForm() {
		 frame = new JFrame("Teacher Registration");
		   frame.setBounds(100, 900, 400, 300);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().setLayout(null);
	        frame.setLocationRelativeTo(null);
	        frame.setResizable(false);
        frame.getContentPane().removeAll();
        frame.repaint();

        JLabel lblNewLabel = new JLabel("Registration Form");
        lblNewLabel.setBounds(150, 20, 150, 20);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel2 = new JLabel("Username:");
        lblNewLabel2.setBounds(30, 50, 100, 20);
        frame.getContentPane().add(lblNewLabel2);
        JTextField regTusernameField = new JTextField();
        regTusernameField.setBounds(140, 50, 150, 20);
        frame.getContentPane().add(regTusernameField);
        regTusernameField.setColumns(10);

        JLabel lblNewLabel3 = new JLabel("User Type:");
        lblNewLabel3.setBounds(30, 80, 100, 20);
        frame.getContentPane().add(lblNewLabel3);
        JTextField regTusertypeField = new JTextField();
        regTusertypeField.setBounds(140, 80, 150, 20);
        frame.getContentPane().add(regTusertypeField);
        regTusertypeField.setColumns(10);
        
        JLabel lblNewLabel4 = new JLabel("User ID:");
        lblNewLabel4.setBounds(30, 110, 100, 20);
        frame.getContentPane().add(lblNewLabel4);
        JTextField regTuseridField = new JTextField();
        regTuseridField.setBounds(140, 110, 150, 20);
        frame.getContentPane().add(regTuseridField);
        regTuseridField.setColumns(10);
        
        
        JLabel lblNewLabel5 = new JLabel("Password:");
        lblNewLabel5.setBounds(30, 140, 100, 20);
        frame.getContentPane().add(lblNewLabel5);
        JPasswordField regTpasswordField = new JPasswordField();
        regTpasswordField.setBounds(140, 140, 150, 20);
        frame.getContentPane().add(regTpasswordField);
        
        JButton registerSubmitButton = new JButton("Register");
        registerSubmitButton.setBounds(30, 180, 100, 30);
        frame.getContentPane().add(registerSubmitButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(140, 180, 100, 30);
        frame.getContentPane().add(backButton);
        

        registerSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = regTusernameField.getText();
                String password = new String(regTpasswordField.getPassword());
                String Type = regTusertypeField.getText();
                String userId = regTuseridField.getText();

                if (username.isEmpty()  || Type.isEmpty()|| userId.isEmpty()|| password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all the fields.");
                } else {
                    TregisterUser(username, Type,userId, password);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.dispose();
                Registration registration = new Registration();
            }
        });

        frame.setVisible(true);
    }

    private void TregisterUser(String username,String Type, String userId, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Tregistration (Tusername, usertype, TuserId , password) VALUES (?, ?, ?, ? )");
            statement.setString(1, username);
            statement.setString(2, Type);
            statement.setString(3, userId);
            statement.setString(4, password);
            statement.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Registration successful.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred during registration.");
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
