package DATABASE_EXP2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
import java.awt.*;

public class TeacherInfoFrame extends JFrame {
	 private String username;
	    private Connection connection;
	    private JButton searchButton;
	    private JButton logoutButton;
	    private JButton GiveStudentScore;
	    private JPanel infoPanel;

	    public TeacherInfoFrame(Connection connection, String username) {
	        this.connection = connection;
	        this.username = username;

	        initialize();
	        loadTeacherInformation();
	    }

	    private void initialize() {
	        setTitle("Teacher Information");
	        setSize(700, 400);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);

	        JPanel mainPanel = new JPanel();
	        mainPanel.setLayout(new BorderLayout());
	        mainPanel.setBackground(new Color(100, 240, 240));

	        JPanel topPanel = new JPanel();
	        topPanel.setBackground(new Color(51, 153, 255));
	        topPanel.setPreferredSize(new Dimension(400, 50));

	        JLabel titleLabel = new JLabel("Teacher Information");
	        titleLabel.setForeground(Color.WHITE);
	        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
	        topPanel.add(titleLabel);

	        infoPanel = new JPanel();
	        infoPanel.setLayout(new GridBagLayout());
	        infoPanel.setBackground(Color.WHITE);

	        JPanel bottomPanel = new JPanel();
	        bottomPanel.setBackground(new Color(240, 240, 240));
	        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

	        searchButton = new JButton("Search Student");
	        searchButton.setPreferredSize(new Dimension(100, 30));
	        searchButton.setBackground(new Color(0, 204, 102));
	        searchButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	SearchStudent STInfo = new SearchStudent();
	            }
	        });

	        logoutButton = new JButton("Logout");
	        logoutButton.setPreferredSize(new Dimension(100, 30));
	        logoutButton.setBackground(new Color(255, 0, 0));
	        logoutButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	dispose();
	            	Login login = new Login();
	            }
	        });

	        GiveStudentScore = new JButton("Give Score");
	        GiveStudentScore.setPreferredSize(new Dimension(100, 30));
	        GiveStudentScore.setBackground(new Color(255, 0, 0));
	        GiveStudentScore.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	ScoreUpdate Score = new ScoreUpdate();
	            }
	        }); 
	        
	        
	        bottomPanel.add(searchButton);
	        bottomPanel.add(logoutButton);
	        bottomPanel.add(GiveStudentScore);
	        
	        mainPanel.add(topPanel, BorderLayout.NORTH);
	        mainPanel.add(infoPanel, BorderLayout.CENTER);
	        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

	        setContentPane(mainPanel);
	    }

	    private void loadTeacherInformation() {
	        try {
	            String query = "SELECT * FROM teachers WHERE name = ?";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, username);
	            ResultSet resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                String id = resultSet.getString("id");
	                String name = resultSet.getString("name");
	                String Course = resultSet.getString("courses");


	                GridBagConstraints gbc = new GridBagConstraints();
	                gbc.gridx = 0;
	                gbc.gridy = 0;
	                gbc.anchor = GridBagConstraints.WEST;
	                gbc.insets = new Insets(10, 10, 10, 10);

	                JLabel idLabel = new JLabel("ID:");
	                JLabel nameLabel = new JLabel("Name:");
	                JLabel courseLabel = new JLabel("Courses:");


	                JLabel idValueLabel = new JLabel(id);
	                JLabel nameValueLabel = new JLabel(name);
	                JLabel courseValueLabel = new JLabel(Course);


	                infoPanel.add(idLabel, gbc);
	                gbc.gridx = 1;
	                infoPanel.add(idValueLabel, gbc);

	                gbc.gridy++;
	                gbc.gridx = 0;
	                infoPanel.add(nameLabel, gbc);
	                gbc.gridx = 1;
	                infoPanel.add(nameValueLabel, gbc);

	                gbc.gridy++;
	                gbc.gridx = 0;
	                infoPanel.add(courseLabel, gbc);
	                gbc.gridx = 1;
	                infoPanel.add(courseValueLabel, gbc);


	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Failed to load student information.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
}
