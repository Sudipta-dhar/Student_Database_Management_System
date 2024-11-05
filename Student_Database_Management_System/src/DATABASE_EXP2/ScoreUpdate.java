package DATABASE_EXP2;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class ScoreUpdate {
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JTable  courseChooseTable;
    private DefaultTableModel  courseChooseTableModel;
    private JTextField studentidField, courseidField, teacheridField,ChosenyearField,scoreField;

       
    private JButton modifycoursechooseButton;
    private Connection connection;

    
    public ScoreUpdate () {
    	ScoreGUI ()	;
        connectToDatabase();
        loadCousreChooseData();
    }
    
    
    private void ScoreGUI () {
    	  frame = new JFrame("Database");
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.setSize(900, 700);
          frame.setResizable(false);
          frame.setLocationRelativeTo(null);
          tabbedPane = new JTabbedPane();

      ;

          //Course Choose Panel
          JPanel coursechoosePanel = createCourseChoosePanel();
          tabbedPane.addTab("Courses Choose", coursechoosePanel);
          
          JButton backButton = new JButton("Back to Mainmenu");
          backButton.setBounds(720, 627, 150, 30);
          frame.getContentPane().add(backButton);
          backButton.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  frame.getContentPane().removeAll();
                  frame.dispose();
                  TeacherInfoFrame tdb = new TeacherInfoFrame(connection, null);
                 
              }
          });

          frame.getContentPane().add(tabbedPane);
          frame.setVisible(true);
      }	
    
    //Create Course Choose Panel
    private JPanel createCourseChoosePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel formPanel = createCourseChooseFormPanel();
        panel.add(formPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        courseChooseTableModel = new DefaultTableModel(new Object[]{"Student ID", "Course ID", "Teacher ID", "Chosen Year", "Score" }, 0);
        courseChooseTable = new JTable(courseChooseTableModel);
        courseChooseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseChooseTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = courseChooseTable.getSelectedRow();
            if (selectedRow != -1) {
                String studentid = (String) courseChooseTableModel.getValueAt(selectedRow, 0);
                String courseid = (String) courseChooseTableModel.getValueAt(selectedRow, 1);
                String teacherid = (String) courseChooseTableModel.getValueAt(selectedRow, 2);
                int ChosenYear = (int) courseChooseTableModel.getValueAt(selectedRow, 3);
                int score = (int) courseChooseTableModel.getValueAt(selectedRow, 4);
              

                studentidField.setText(studentid);
                courseidField.setText(courseid);
                teacheridField.setText(teacherid);
                ChosenyearField.setText(String.valueOf(ChosenYear));
                scoreField.setText(String.valueOf(score));
               
            }
        });
        scrollPane.setViewportView(courseChooseTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = createCourseChooseButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
    
    private JPanel createCourseChooseFormPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2));

        JLabel courseChooseStudentIdLabel = new JLabel("Student ID:");
        studentidField = new JTextField();
        panel.add(courseChooseStudentIdLabel);
        panel.add(studentidField);

        JLabel courseidLabel = new JLabel("Course Id:");
        courseidField = new JTextField();
        panel.add(courseidLabel);
        panel.add(courseidField);

        JLabel teacheridLabel = new JLabel("Teacher ID:");
        teacheridField = new JTextField();
        panel.add(teacheridLabel);
        panel.add(teacheridField);

        JLabel ChosenYearLabel = new JLabel("Chosen Year:");
        ChosenyearField = new JTextField();
        panel.add(ChosenYearLabel);
        panel.add(ChosenyearField);

        JLabel scoreLabel = new JLabel("Score:");
        scoreField = new JTextField();
        panel.add(scoreLabel);
        panel.add(scoreField);

        return panel;
    }
    
    private JPanel createCourseChooseButtonPanel() {
        JPanel panel = new JPanel();


        modifycoursechooseButton = new JButton("Modify Course Score");
        modifycoursechooseButton.addActionListener(e -> modifyCourseChooseScore());
        panel.add(modifycoursechooseButton);

        return panel;
    }
   
    private void loadCousreChooseData() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM course_choosing");
            while (resultSet.next()) {
                String studentid = resultSet.getString("student_id");
                String courseid = resultSet.getString("course_id");
                String teacherid = resultSet.getString("teacher_id");
                int ChosenYear = resultSet.getInt("chosen_year");
                int score = resultSet.getInt("score");
                Object[] rowData = {studentid, courseid, teacherid, ChosenYear, score};
                courseChooseTableModel.addRow(rowData);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while loading course choosing data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void modifyCourseChooseScore() {
        int selectedRow = courseChooseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a course to modify.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    	
    	String studentid = (String)studentidField.getText() ;
        String courseid = (String)courseidField.getText();
        String teacherid = (String) teacheridField.getText();
        int ChosenYear = (int)Integer.parseInt (ChosenyearField.getText());
        int score = (int) Integer.parseInt (scoreField.getText());
        
        try {
            String query = "UPDATE course_choosing SET score = ? WHERE student_id = ? AND course_id = ? AND teacher_id = ? AND chosen_year = ? ";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, score);
            statement.setString(2, studentid);
            statement.setString(3, courseid);
            statement.setString(4, teacherid);
            statement.setInt(5, ChosenYear);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {

                courseChooseTableModel.setValueAt(studentid, selectedRow, 0);
                courseChooseTableModel.setValueAt(courseid, selectedRow, 1);
                courseChooseTableModel.setValueAt(teacherid, selectedRow, 2);
                courseChooseTableModel.setValueAt(ChosenYear, selectedRow, 3);
                courseChooseTableModel.setValueAt(score, selectedRow, 4);

                clearCourseChooseFields();
                JOptionPane.showMessageDialog(frame, "Course Choose modified successfully.");
            } else {
                JOptionPane.showMessageDialog(frame,"No matching course choose found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame,"Failed to modify course choose score.");
        }
    }
    
    private void clearCourseChooseFields() {
    	studentidField.setText("");
    	courseidField.setText("");
    	teacheridField.setText("");
    	ChosenyearField.setText("");
    	scoreField.setText("");

    }
    
    private void connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/college_db";
            String username = "root";
            String password = "Ssd@#1234567890";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }  
    
}
    
    
    
    
    

