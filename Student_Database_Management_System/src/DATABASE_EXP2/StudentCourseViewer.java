package DATABASE_EXP2;

import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class StudentCourseViewer {
	
	  private Connection connection;
	    private JFrame frame;
	    private JPanel panel;
	    private JPanel contentPanel;
	    private JButton backButton;
	    private JButton searchButton;
	    private boolean searchMode;
	    
	    public StudentCourseViewer() {
	        connectToDatabase();
	        initializeUI();
	        searchMode = false;
	    }

	    private void connectToDatabase() {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/college_db", "root", "Ssd@#1234567890");
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
	            System.exit(1);
	        }
	    }

	    private void initializeUI() {
	        frame = new JFrame("Student Course Viewer");
	        frame.setSize(900, 900);
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	        panel = new JPanel(new BorderLayout());
	        panel.setBackground(new Color(135, 206, 235));

	        JLabel titleLabel = new JLabel("Student Course Viewer");
	        titleLabel.setForeground(Color.WHITE);
	        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
	        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
	        panel.add(titleLabel, BorderLayout.NORTH);

	        contentPanel = new JPanel(new GridLayout(0, 1, 10, 10));
	        
	        contentPanel.setBackground(frame.getContentPane().getBackground());
	        JScrollPane scrollPane = new JScrollPane(contentPanel);

	        // Adjust panel size to cover 80% of the frame
	        int panelHeight = (int) (frame.getHeight() * 0.8);
	        //int panelWidth =(int) (frame.getWidth()/2);
	        		
	       //scrollPane.setPreferredSize(new Dimension(frame.getHeight(), panelWidth));
	        scrollPane.setPreferredSize(new Dimension(frame.getWidth(), panelHeight));

	        panel.add(scrollPane, BorderLayout.CENTER);

	        searchButton = new JButton("Search");
	        searchButton.setBackground(new Color(70, 130, 180));
	        searchButton.setForeground(Color.WHITE);
	        searchButton.setFocusPainted(false);
	        searchButton.addActionListener(new ActionListener() {
	        	
	            	public void actionPerformed(ActionEvent e) {
	            	    if (!searchMode) {
	            	        String studentId;
	            	        do {
	            	            studentId = JOptionPane.showInputDialog(frame, "Enter your Student ID:");
	            	            if (studentId == null || studentId.isEmpty()) {
	            	                JOptionPane.showMessageDialog(frame, "Invalid Student ID.", "Error", JOptionPane.ERROR_MESSAGE);
	            	            } else if (!isStudentIdValid(studentId)) {
	            	                JOptionPane.showMessageDialog(frame, "Student ID does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
	            	            }
	            	        } while (studentId != null && !studentId.isEmpty() && !isStudentIdValid(studentId));

	            	        if (studentId != null && !studentId.isEmpty()) {
	            	            viewStudentCourses(studentId);
	            	            searchMode = true;
	            	            searchButton.setText("Back to Search");
	            	        }
	            	    } else {
	            	        clearContentPanel();
	            	        contentPanel.setVisible(false); // Hide the content panel
	            	        searchMode = false;
	            	        searchButton.setText("Search");

	            	        String studentId;
	            	        do {
	            	            studentId = JOptionPane.showInputDialog(frame, "Enter your Student ID:");
	            	            if (studentId == null || studentId.isEmpty()) {
	            	                JOptionPane.showMessageDialog(frame, "Invalid Student ID.", "Error", JOptionPane.ERROR_MESSAGE);
	            	            } else if (!isStudentIdValid(studentId)) {
	            	                JOptionPane.showMessageDialog(frame, "Student ID does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
	            	            }
	            	        } while (studentId != null && !studentId.isEmpty() && !isStudentIdValid(studentId));

	            	        if (studentId != null && !studentId.isEmpty()) {
	            	            viewStudentCourses(studentId);
	            	            searchMode = true;
	            	            searchButton.setText("Back to Search");
	            	        }

	            	        contentPanel.setVisible(true); // Show the content panel again
	            	    }
	            	
	            }
	        });
	        

	        backButton = new JButton("Back");
	        backButton.setBackground(new Color(70, 130, 180));
	        backButton.setForeground(Color.WHITE);
	        backButton.setFocusPainted(false);
	        backButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                frame.dispose();
	              StudentInfoFrame sdb = new StudentInfoFrame(connection, null);
	              sdb.isVisible();
	            }
	        });

	        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	        buttonPanel.setBackground(new Color(135, 206, 235));
	        buttonPanel.add(searchButton);
	        buttonPanel.add(backButton);
	        panel.add(buttonPanel, BorderLayout.SOUTH);

	        frame.setContentPane(new JPanel() {
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                Graphics2D g2d = (Graphics2D) g;
	                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	                Color startColor = new Color(135, 206, 250);
	                Color endColor = new Color(176, 224, 230);
	                GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, getHeight(), endColor);
	                g2d.setPaint(gradient);
	                g2d.fillRect(0, 0, getWidth(), getHeight());
	            }
	        });

	        frame.add(panel);
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	    }

	    private void clearContentPanel() {
	        panel.removeAll();
	        frame.revalidate();
	        frame.repaint();
	    }
	    private boolean isStudentIdValid(String studentId) {
	        try {
	            String query = "SELECT COUNT(*) FROM students WHERE id = ?";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, studentId);
	            ResultSet resultSet = statement.executeQuery();
	            resultSet.next();
	            int count = resultSet.getInt(1);
	            return count > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }

	    private void viewStudentCourses(String studentId) {
	        try {
	            String query = "SELECT c.name, c.id, c.credit, c.grade, cc.score " +
	                    "FROM courses c " +
	                    "JOIN course_choosing cc ON c.id = cc.course_id " +
	                    "WHERE cc.student_id = ?";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, studentId);
	            ResultSet resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                String courseName = resultSet.getString("name");
	                String courseId = resultSet.getString("id");
	                int credit = resultSet.getInt("credit");
	                int grade = resultSet.getInt("grade");
	                int score = resultSet.getInt("score");

	                JLabel courseLabel = new JLabel("Course Name: " + courseName);
	                contentPanel.add(courseLabel);

	                JLabel idLabel = new JLabel("Course ID: " + courseId);
	                contentPanel.add(idLabel);

	                JLabel creditLabel = new JLabel("Credit: " + credit);
	                contentPanel.add(creditLabel);

	                JLabel gradeLabel = new JLabel("Grade: " + grade);
	                contentPanel.add(gradeLabel);

	                JLabel scoreLabel = new JLabel("Score: " + score);
	                contentPanel.add(scoreLabel);

	                contentPanel.add(new JSeparator());
	            }

	            resultSet.close();

	            frame.revalidate();
	            frame.repaint();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(frame, "Error occurred while viewing student courses.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
/*
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            StudentCourseViewer viewer = new StudentCourseViewer();
	            viewer.frame.setVisible(true);
	        });
	    }
	
	*/
	
	
	
	
  
}