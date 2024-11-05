package DATABASE_EXP2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentInfoFrame extends JFrame {
    private String username;
    private Connection connection;
    private JButton searchButton;
    private JButton logoutButton;
    private JButton viewCoursesButton;
    private JPanel infoPanel;

    public StudentInfoFrame(Connection connection, String username) {
        this.connection = connection;
        this.username = username;

        initialize();
        loadStudentInformation();
    }

    private void initialize() {
        setTitle("Student Information");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(51, 153, 255));
        topPanel.setPreferredSize(new Dimension(400, 50));

        JLabel titleLabel = new JLabel("Student Information");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(titleLabel);

        infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        infoPanel.setBackground(Color.WHITE);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(240, 240, 240));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        searchButton = new JButton("Search");
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
        viewCoursesButton = new JButton("Course");
        viewCoursesButton.setPreferredSize(new Dimension(100, 30));
        viewCoursesButton.setBackground(new Color(255, 255, 0));
        viewCoursesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            StudentCourseViewer sv = new StudentCourseViewer();
            }
        });
        bottomPanel.add(viewCoursesButton);
        bottomPanel.add(searchButton);
        bottomPanel.add(logoutButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void loadStudentInformation() {
        try {
            String query = "SELECT * FROM students WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String sex = resultSet.getString("sex");
                int entranceAge = resultSet.getInt("entrance_age");
                int entranceYear = resultSet.getInt("entrance_year");
                String studentClass = resultSet.getString("class");

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.WEST;
                gbc.insets = new Insets(10, 10, 10, 10);

                JLabel idLabel = new JLabel("ID:");
                JLabel nameLabel = new JLabel("Name:");
                JLabel sexLabel = new JLabel("Sex:");
                JLabel entranceAgeLabel = new JLabel("Entrance Age:");
                JLabel entranceYearLabel = new JLabel("Entrance Year:");
                JLabel classLabel = new JLabel("Class:");

                JLabel idValueLabel = new JLabel(id);
                JLabel nameValueLabel = new JLabel(name);
                JLabel sexValueLabel = new JLabel(sex);
                JLabel entranceAgeValueLabel = new JLabel(Integer.toString(entranceAge));
                JLabel entranceYearValueLabel = new JLabel(Integer.toString(entranceYear));
                JLabel classValueLabel = new JLabel(studentClass);

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
                infoPanel.add(sexLabel, gbc);
                gbc.gridx = 1;
                infoPanel.add(sexValueLabel, gbc);

                gbc.gridy++;
                gbc.gridx = 0;
                infoPanel.add(entranceAgeLabel, gbc);
                gbc.gridx = 1;
                infoPanel.add(entranceAgeValueLabel, gbc);

                gbc.gridy++;
                gbc.gridx = 0;
                infoPanel.add(entranceYearLabel, gbc);
                gbc.gridx = 1;
                infoPanel.add(entranceYearValueLabel, gbc);

                gbc.gridy++;
                gbc.gridx = 0;
                infoPanel.add(classLabel, gbc);
                gbc.gridx = 1;
                infoPanel.add(classValueLabel, gbc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load student information.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

}