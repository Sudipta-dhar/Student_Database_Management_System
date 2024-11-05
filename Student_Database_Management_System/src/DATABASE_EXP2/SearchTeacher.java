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

public class SearchTeacher {
	
	    private JFrame frame;
	    private JTextField teacherIdField;
	    private JTextField teacherNameField;
	    private JTextField subjectField;
	    private JTable teacherTable;
	    private JButton searchButton;
	    private DefaultTableModel tableModel;
	    private Connection connection;

	    public SearchTeacher() {
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


	        searchButton = new JButton("Search");

	        formPanel.add(teacherIdLabel);
	        formPanel.add(teacherIdField);
	        formPanel.add(teacherNameLabel);
	        formPanel.add(teacherNameField);
	        formPanel.add(subjectLabel);
	        formPanel.add(subjectField);

	        formPanel.add(searchButton);


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
