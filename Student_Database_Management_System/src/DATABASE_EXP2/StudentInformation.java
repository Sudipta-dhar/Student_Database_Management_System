package DATABASE_EXP2;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentInformation {
    private JFrame frame;
    private Connection connection;
    private JTextField idField;
    private JTextField nameField;

    public StudentInformation() {
        setupGUI();
        connectToDatabase();
    }

    private void setupGUI() {
        frame = new JFrame("Student Information");
        frame.setBounds(100, 300, 400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(null);

        JLabel lblId = new JLabel("Student ID:");
        lblId.setBounds(30, 20, 100, 20);
        frame.getContentPane().add(lblId);

        idField = new JTextField();
        idField.setBounds(140, 20, 200, 20);
        frame.getContentPane().add(idField);
        idField.setColumns(10);

        JLabel lblName = new JLabel("Student Name:");
        lblName.setBounds(30, 50, 100, 20);
        frame.getContentPane().add(lblName);

        nameField = new JTextField();
        nameField.setBounds(140, 50, 200, 20);
        frame.getContentPane().add(nameField);
        nameField.setColumns(10);

        JButton getInfoButton = new JButton("Get Information");
        getInfoButton.setBounds(140, 90, 150, 30);
        frame.getContentPane().add(getInfoButton);

        getInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = idField.getText();
                String studentName = nameField.getText();

                if (studentId.isEmpty() || studentName.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter student ID and name.");
                } else {
                    getStudentInformation(studentId, studentName);
                }
            }
        });
        JButton backButton = new JButton("<=");
        backButton.setBounds(320, 130, 50, 20);
        backButton.setBackground(Color.RED);
        frame.getContentPane().add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.dispose();
                StudentInfoFrame sdb =new StudentInfoFrame(connection, null);
            }
        });

        frame.setVisible(true);
    }
    
    private void getStudentInformation(String studentId, String studentName) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE id = ? AND name = ?");
            statement.setString(1, studentId);
            statement.setString(2, studentName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String sex = resultSet.getString("sex");
                int entrance_age = resultSet.getInt("entrance_age");
                String classes = resultSet.getString("class");

                String information = "Student Information:\n\n" +
                        "ID: " + id + "\n" +
                        "Name: " + name + "\n" +
                        "Sex: " + sex + "\n" +
                        "Entrance Age: " + entrance_age + "\n" +
                        "Class: " + classes  ;
                JOptionPane.showMessageDialog(frame, information);
            } else {
                JOptionPane.showMessageDialog(frame, "No information found for the provided ID and name.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while fetching student information.", "Error", JOptionPane.ERROR_MESSAGE);
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