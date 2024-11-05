package DATABASE_EXP2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SearchStudent {

	    private JFrame frame;
	    private JTextField studentIdField;
	    private JTextField studentNameField;
	    private JTextField sexField;
	    private JTextField entranceAgeField;
	    private JTextField entranceYearField;
	    private JTextField classNameField;
	    private JTable studentTable;
	    private DefaultTableModel tableModel;
	    private Connection connection;
	    private JButton searchButton;
	    private JButton BackButton;
	    public SearchStudent() {
	        initialize();
	        connectToDatabase();
	        loadStudentData();
	    }

	    private void initialize() {
	    	
	        frame = new JFrame("Student Database");
	        frame.setBounds(100, 100, 950, 500);
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
	   
	        searchButton = new JButton("Search");
	        BackButton = new JButton("<=");
	        
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
	        formPanel.add(searchButton);
	        formPanel.add(BackButton);
	
	        searchButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                searchStudent();
	            }
	        });
	        BackButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                frame.getContentPane().removeAll();
	                frame.dispose();
	            	StudentInfoFrame sdb = new StudentInfoFrame(connection, null);
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

	    private void searchStudent() {
	        String studentId = studentIdField.getText();

	        try {
	            String query = "SELECT * FROM students WHERE id = ?";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, studentId);
	            ResultSet resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                String studentName = resultSet.getString("name");
	                String sex = resultSet.getString("sex");
	                int entranceAge = resultSet.getInt("entrance_age");
	                int entranceYear = resultSet.getInt("entrance_year");
	                String studentClass = resultSet.getString("class");

	                studentNameField.setText(studentName);
	                sexField.setText(sex);
	                entranceAgeField.setText(String.valueOf(entranceAge));
	                entranceYearField.setText(String.valueOf(entranceYear));
	                classNameField.setText(studentClass);
	            } else {
	                JOptionPane.showMessageDialog(frame, "Student not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
	                clearForm();
	            }

	            resultSet.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(frame, "Error occurred while searching for student.", "Error", JOptionPane.ERROR_MESSAGE);
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
