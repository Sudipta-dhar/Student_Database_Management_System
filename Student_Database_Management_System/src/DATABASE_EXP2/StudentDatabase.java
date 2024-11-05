package DATABASE_EXP2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StudentDatabase {
    private JFrame frame;
    private JTextField studentIdField;
    private JTextField studentNameField;
    private JTextField sexField;
    private JTextField entranceAgeField;
    private JTextField entranceYearField;
    private JTextField classNameField;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private Connection connection;

    public StudentDatabase() {
        initialize();
        connectToDatabase();
        loadStudentData();
    }

    private void initialize() {
        frame = new JFrame("Student Database");
        frame.setBounds(100, 100, 800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        JPanel formPanel = createFormPanel();
        JPanel tablePanel = createTablePanel();

        frame.getContentPane().add(formPanel, BorderLayout.WEST);
        frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setPreferredSize(new Dimension(300, 700));
        formPanel.setLayout(new GridLayout(10, 7, 40, 20));
        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdField = new JTextField();      
        JLabel studentNameLabel = new JLabel("Student Name:");
        studentNameField = new JTextField();
        JLabel sexLabel = new JLabel("Sex:");
        sexField = new JTextField();
        JLabel entranceAgeLabel = new JLabel("Entrance Age:");
        entranceAgeField = new JTextField();
        JLabel entranceYearLabel = new JLabel("Entrance Year:");
        entranceYearField = new JTextField();
        JLabel classNameLabel = new JLabel("Class Name:");
        classNameField = new JTextField();
        addButton = new JButton("Add");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");

        formPanel.add(studentIdLabel);
        formPanel.add(studentIdField);
        formPanel.add(studentNameLabel);
        formPanel.add(studentNameField);
        formPanel.add(sexLabel);
        formPanel.add(sexField);
        formPanel.add(entranceAgeLabel);
        formPanel.add(entranceAgeField);
        formPanel.add(entranceYearLabel);
        formPanel.add(entranceYearField);
        formPanel.add(classNameLabel);
        formPanel.add(classNameField);
        formPanel.add(addButton);
        formPanel.add(modifyButton);
        formPanel.add(deleteButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyStudent();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });

        return formPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Student ID");
        tableModel.addColumn("Student Name");
        tableModel.addColumn("Sex");
        tableModel.addColumn("Entrance Age");
        tableModel.addColumn("Entrance Year");
        tableModel.addColumn("Class Name");
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/college_db", "root","Ssd@#1234567890");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadStudentData() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");

            while (resultSet.next()) {
                String studentId = resultSet.getString("id");
                String studentName = resultSet.getString("name");
                String sex = resultSet.getString("sex");
                String entranceAge = resultSet.getString("entrance_age");
                String entranceyear= resultSet.getString("entrance_year");
                String className = resultSet.getString("class");

                Object[] rowData = {studentId, studentName, sex, entranceAge,entranceyear, className};
                tableModel.addRow(rowData);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while loading student data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addStudent() {
        String studentId = studentIdField.getText();
        String studentName = studentNameField.getText();
        String sex = sexField.getText();
        String entranceAge = entranceAgeField.getText();
        String entranceyear= entranceYearField.getText();
        String className = classNameField.getText();

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO students (id, name, sex, entrance_age, entrance_year, class) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, studentId);
            statement.setString(2, studentName);
            statement.setString(3, sex);
            statement.setString(4, entranceAge);
            statement.setString(5, entranceyear);
            statement.setString(6, className);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                Object[] rowData = {studentId, studentName, sex, entranceAge, entranceyear, className};
                tableModel.addRow(rowData);
                clearForm();
                JOptionPane.showMessageDialog(frame, "Student added successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to add student.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while adding student.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifyStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a student to modify.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String studentId = studentIdField.getText();
        String studentName = studentNameField.getText();
        String sex = sexField.getText();
        String entranceAge = entranceAgeField.getText();
        String entranceyear= entranceYearField.getText();
        String className = classNameField.getText();
        try {
            String oldStudentId = (String) tableModel.getValueAt(selectedRow, 0);
            PreparedStatement statement = connection.prepareStatement("UPDATE students SET id=?, name=?, sex=?, entrance_age=?, entrance_year=?,class=? WHERE id=?");
            statement.setString(1, studentId);
            statement.setString(2, studentName);
            statement.setString(3, sex);
            statement.setString(4, entranceAge);
            statement.setString(5, entranceyear);
            statement.setString(6, className);
            statement.setString(7, oldStudentId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                tableModel.setValueAt(studentId, selectedRow, 0);
                tableModel.setValueAt(studentName, selectedRow, 1);
                tableModel.setValueAt(sex, selectedRow, 2);
                tableModel.setValueAt(entranceAge, selectedRow, 3);
                tableModel.setValueAt(entranceyear, selectedRow, 4);
                tableModel.setValueAt(className, selectedRow, 5);
                clearForm();
                JOptionPane.showMessageDialog(frame, "Student modified successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to modify student.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while modifying student.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a student to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String studentId = (String) tableModel.getValueAt(selectedRow, 0);

        int confirmation = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this student?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM students WHERE id=?");
                statement.setString(1, studentId);

                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    tableModel.removeRow(selectedRow);
                    clearForm();
                    JOptionPane.showMessageDialog(frame, "Student deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to delete student.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error occurred while deleting student.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        studentIdField.setText("");
        studentNameField.setText("");
        sexField.setText("");
        entranceAgeField.setText("");
        entranceYearField.setText("");
        classNameField.setText("");
    }

}
