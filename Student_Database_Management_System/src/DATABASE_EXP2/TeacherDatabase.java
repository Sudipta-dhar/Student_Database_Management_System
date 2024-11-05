package DATABASE_EXP2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TeacherDatabase {
    private JFrame frame;
    private JTextField teacherIdField;
    private JTextField teacherNameField;
    private JTextField subjectField;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JTable teacherTable;
    private JButton searchButton;
    private DefaultTableModel tableModel;
    private Connection connection;

    public TeacherDatabase() {
        initialize();
        connectToDatabase();
        loadTeacherData();
    }

    private void initialize() {
        frame = new JFrame("Teacher Database");
        frame.setBounds(100, 100, 900, 500);
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
        formPanel.setPreferredSize(new Dimension(300, 0));
        formPanel.setLayout(new GridLayout(8, 7, 10, 10));

        JLabel teacherIdLabel = new JLabel("Teacher ID:");
        teacherIdField = new JTextField();
        JLabel teacherNameLabel = new JLabel("Teacher Name:");
        teacherNameField = new JTextField();
        JLabel subjectLabel = new JLabel("Cource:");
        subjectField = new JTextField();

        addButton = new JButton("Add");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
        searchButton = new JButton("Search");

        formPanel.add(teacherIdLabel);
        formPanel.add(teacherIdField);
        formPanel.add(teacherNameLabel);
        formPanel.add(teacherNameField);
        formPanel.add(subjectLabel);
        formPanel.add(subjectField);
        formPanel.add(addButton);
        formPanel.add(modifyButton);
        formPanel.add(deleteButton);
        formPanel.add(searchButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTeacher();
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyTeacher();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTeacher();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchTeacher();
            }
        });


        return formPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Teacher ID");
        tableModel.addColumn("Teacher Name");
        tableModel.addColumn("Subject");

        teacherTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(teacherTable);

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

    private void loadTeacherData() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM teachers");

            while (resultSet.next()) {
                String teacherId = resultSet.getString("id");
                String teacherName = resultSet.getString("name");
                String subject = resultSet.getString("courses");

                Object[] rowData = {teacherId, teacherName, subject};
                tableModel.addRow(rowData);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while loading teacher data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addTeacher() {
        String teacherId = teacherIdField.getText();
        String teacherName = teacherNameField.getText();
        String subject = subjectField.getText();

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO teachers (id, name, courses) VALUES (?, ?, ?)");
            statement.setString(1, teacherId);
            statement.setString(2, teacherName);
            statement.setString(3, subject);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                Object[] rowData = {teacherId, teacherName, subject};
                tableModel.addRow(rowData);
                clearForm();
                JOptionPane.showMessageDialog(frame, "Teacher added successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to add teacher.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while adding teacher.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifyTeacher() {
        int selectedRow = teacherTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a teacher to modify.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String teacherId = teacherIdField.getText();
        String teacherName = teacherNameField.getText();
        String subject = subjectField.getText();
        try {
            String oldTeacherId = (String) tableModel.getValueAt(selectedRow, 0);
            PreparedStatement statement = connection.prepareStatement("UPDATE teachers SET id=?, name=?, courses=? WHERE id=?");
            statement.setString(1, teacherId);
            statement.setString(2, teacherName);
            statement.setString(3, subject);
            statement.setString(4, oldTeacherId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                tableModel.setValueAt(teacherId, selectedRow, 0);
                tableModel.setValueAt(teacherName, selectedRow, 1);
                tableModel.setValueAt(subject, selectedRow, 2);
                clearForm();
                JOptionPane.showMessageDialog(frame, "Teacher modified successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to modify teacher.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while modifying teacher.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTeacher() {
        int selectedRow = teacherTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a teacher to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String teacherId = (String) tableModel.getValueAt(selectedRow, 0);

        int confirmation = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this teacher?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM teachers WHERE id=?");
                statement.setString(1, teacherId);

                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    tableModel.removeRow(selectedRow);
                    clearForm();
                    JOptionPane.showMessageDialog(frame, "Teacher deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to delete teacher.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error occurred while deleting teacher.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchTeacher() {
        String teacherId = teacherIdField.getText();

        try {
            String query = "SELECT * FROM teachers WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, teacherId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String teacherName = resultSet.getString("name");
                String subject = resultSet.getString("courses");

                teacherNameField.setText(teacherName);
                subjectField.setText(subject);
            } else {
                JOptionPane.showMessageDialog(frame, "Teacher not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            }

            resultSet.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while searching for teacher.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearForm() {
        teacherIdField.setText("");
        teacherNameField.setText("");
        subjectField.setText("");
    }

    
}