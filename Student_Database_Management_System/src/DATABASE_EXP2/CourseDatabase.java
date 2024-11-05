package DATABASE_EXP2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CourseDatabase {
    private JFrame frame;
    private JTextField courseIdField;
    private JTextField courseNameField;
    private JTextField teacherIdField;
    private JTextField creditField;
    private JTextField gradeField;
    private JTextField canceledYearField;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JTable courseTable;
    private DefaultTableModel tableModel;
    private Connection connection;

    public CourseDatabase() {
        initialize();
        connectToDatabase();
        loadCourseData();
    }

    private void initialize() {
        frame = new JFrame("Course Database");
        frame.setBounds(100, 100, 1000, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        JPanel formPanel = createFormPanel();
        JPanel tablePanel = createTablePanel();

        frame.getContentPane().add(formPanel, BorderLayout.WEST);
        frame.getContentPane().add(tablePanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setPreferredSize(new Dimension(350, 0));
        formPanel.setLayout(new GridLayout(10, 3, 10, 10));

        JLabel courseIdLabel = new JLabel("Course ID:");
        courseIdField = new JTextField();
        JLabel courseNameLabel = new JLabel("Course Name:");
        courseNameField = new JTextField();
        JLabel teacherIdLabel = new JLabel("Teacher ID:");
        teacherIdField = new JTextField();
        JLabel creditLabel = new JLabel("Credit:");
        creditField = new JTextField();
        JLabel gradeLabel = new JLabel("Grade:");
        gradeField = new JTextField();
        JLabel canceledYearLabel = new JLabel("Canceled Year:");
        canceledYearField = new JTextField();

        addButton = new JButton("Add");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");

        formPanel.add(courseIdLabel);
        formPanel.add(courseIdField);
        formPanel.add(courseNameLabel);
        formPanel.add(courseNameField);
        formPanel.add(teacherIdLabel);
        formPanel.add(teacherIdField);
        formPanel.add(creditLabel);
        formPanel.add(creditField);
        formPanel.add(gradeLabel);
        formPanel.add(gradeField);
        formPanel.add(canceledYearLabel);
        formPanel.add(canceledYearField);
        formPanel.add(addButton);
        formPanel.add(modifyButton);
        formPanel.add(deleteButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourse();
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyCourse();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCourse();
            }
        });

        return formPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Course ID");
        tableModel.addColumn("Course Name");
        tableModel.addColumn("Teacher ID");
        tableModel.addColumn("Credit");
        tableModel.addColumn("Grade");
        tableModel.addColumn("Canceled Year");

        courseTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(courseTable);

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
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

    private void loadCourseData() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM courses");

            while (resultSet.next()) {
                String courseId = resultSet.getString("id");
                String courseName = resultSet.getString("name");
                String teacherId = resultSet.getString("teacher_id");
                int credit = resultSet.getInt("credit");
                int grade = resultSet.getInt("grade");
                int canceledYear = resultSet.getInt("canceled_year");

                Object[] rowData = {courseId, courseName, teacherId, credit, grade, canceledYear};
                tableModel.addRow(rowData);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while loading course data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCourse() {
        String courseId = courseIdField.getText();
        String courseName = courseNameField.getText();
        String teacherId = teacherIdField.getText();
        int credit = Integer.parseInt(creditField.getText());
        int grade = Integer.parseInt(gradeField.getText());
        int canceledYear = Integer.parseInt(canceledYearField.getText());

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO courses (id, name, teacher_id, credit, grade, canceled_year) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, courseId);
            statement.setString(2, courseName);
            statement.setString(3, teacherId);
            statement.setInt(4, credit);
            statement.setInt(5, grade);
            statement.setInt(6, canceledYear);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                Object[] rowData = {courseId, courseName, teacherId, credit, grade, canceledYear};
                tableModel.addRow(rowData);
                clearForm();
                JOptionPane.showMessageDialog(frame, "Course added successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to add course.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while adding course.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifyCourse() {
        int selectedRow = courseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a course to modify.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String courseId = courseIdField.getText();
        String courseName = courseNameField.getText();
        String teacherId = teacherIdField.getText();
        int credit = Integer.parseInt(creditField.getText());
        int grade = Integer.parseInt(gradeField.getText());
        int canceledYear = Integer.parseInt(canceledYearField.getText());

        try {
            String oldCourseId = (String) tableModel.getValueAt(selectedRow, 0);
            PreparedStatement statement = connection.prepareStatement("UPDATE courses SET id=?, name=?, teacher_id=?, credit=?, grade=?, canceled_year=? WHERE id=?");
            statement.setString(1, courseId);
            statement.setString(2, courseName);
            statement.setString(3, teacherId);
            statement.setInt(4, credit);
            statement.setInt(5, grade);
            statement.setInt(6, canceledYear);
            statement.setString(7, oldCourseId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                Object[] rowData = {courseId, courseName, teacherId, credit, grade, canceledYear};
                for (int i = 0; i < tableModel.getColumnCount(); i++) {
                    tableModel.setValueAt(rowData[i], selectedRow, i);
                }
                clearForm();
                JOptionPane.showMessageDialog(frame, "Course modified successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to modify course.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while modifying course.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCourse() {
        int selectedRow = courseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a course to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String courseId = (String) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete the selected course?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM courses WHERE id=?");
                statement.setString(1, courseId);

                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    tableModel.removeRow(selectedRow);
                    clearForm();
                    JOptionPane.showMessageDialog(frame, "Course deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to delete course.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error occurred while deleting course.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        courseIdField.setText("");
        courseNameField.setText("");
        teacherIdField.setText("");
        creditField.setText("");
        gradeField.setText("");
        canceledYearField.setText("");
    }


}