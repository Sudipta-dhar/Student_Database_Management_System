package DATABASE_EXP2;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class Admin extends JFrame {
   private Main main;
		private JFrame frame;
		private JButton changeColorButton;
		//student
	    private JTextField studentIdField;
	    private JTextField studentNameField;
	    private JComboBox<String> sexComboBox;
	    private JTextField entranceAgeField;
	    private JTextField entranceYearField;
	    private JTextField classField;
	    private JButton addStudentButton;
		//teacher
	    private JTextField teacherNameField;
	    private JTextField coursesField;
	    private JButton addTeacherButton;
		//course choosing
	    private JTextField studentIdField1;
	    private JTextField courseChoosingIdField;
	    private JTextField teacherChoosingIdField;
	    private JTextField chosenYearField;
	    private JTextField scoreField;
	    private JButton addCourseChoosingButton;
		//course
	    private JTextField courseIdField;
	    private JTextField courseNameField;
	    private JTextField teacherIdField;
	    private JTextField creditField;
	    private JTextField gradeField;
	    private JTextField canceledYearField;
	    private JButton addCourseButton;
	    private JButton searchCourseButton;
	    //result
	    private JTextArea resultTextArea;
	    //login


		    private Connection connection;
		    
			    public Admin() {
			    	main=new Main();
			        initializeGUI();
			        initializeDatabase();
			    }

			    private void initializeGUI() {


			    	frame = new JFrame("College Management System");
			        frame.setBounds(100, 900, 650, 750);
			        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        frame.getContentPane().setLayout(null);
			        frame.setLocationRelativeTo(null);
			        frame.setResizable(false);
			        
			        //Student
			        JLabel lblNewLabel = new JLabel("Student ID:");
			        lblNewLabel.setBounds(20, 20, 100, 20);
			        frame.getContentPane().add(lblNewLabel);
			        studentIdField = new JTextField();
			        studentIdField.setBounds(130, 20, 150, 20);
			        frame.getContentPane().add(studentIdField);
			        studentIdField.setColumns(10);

			        JLabel lblNewLabel_1 = new JLabel("Student Name:");
			        lblNewLabel_1.setBounds(20, 50, 100, 20);
			        frame.getContentPane().add(lblNewLabel_1);
			        studentNameField = new JTextField();
			        studentNameField.setBounds(130, 50, 150, 20);
			        frame.getContentPane().add(studentNameField);
			        studentNameField.setColumns(10);

			        JLabel lblNewLabel_2 = new JLabel("Sex:");
			        lblNewLabel_2.setBounds(20, 80, 100, 20);
			        frame.getContentPane().add(lblNewLabel_2);
			        sexComboBox = new JComboBox<>();
			        sexComboBox.setBounds(130, 80, 150, 20);
			        sexComboBox.addItem("male");
			        sexComboBox.addItem("female");
			        frame.getContentPane().add(sexComboBox);

			        JLabel lblNewLabel_3 = new JLabel("Entrance Age:");
			        lblNewLabel_3.setBounds(20, 110, 100, 20);
			        frame.getContentPane().add(lblNewLabel_3);
			        entranceAgeField = new JTextField();
			        entranceAgeField.setBounds(130, 110, 150, 20);
			        frame.getContentPane().add(entranceAgeField);
			        entranceAgeField.setColumns(10);

			        JLabel lblNewLabel_4 = new JLabel("Entrance Year:");
			        lblNewLabel_4.setBounds(20, 140, 100, 20);
			        frame.getContentPane().add(lblNewLabel_4);
			        entranceYearField = new JTextField();
			        entranceYearField.setBounds(130, 140, 150, 20);
			        frame.getContentPane().add(entranceYearField);
			        entranceYearField.setColumns(10);

			        JLabel lblNewLabel_5 = new JLabel("Class:");
			        lblNewLabel_5.setBounds(20, 170, 100, 20);
			        frame.getContentPane().add(lblNewLabel_5);
			        classField = new JTextField();
			        classField.setBounds(130, 170, 150, 20);
			        frame.getContentPane().add(classField);
			        classField.setColumns(10);
			        
			        //Course
			        JLabel lblNewLabel_6 = new JLabel("Course ID:");
			        lblNewLabel_6.setBounds(300, 20, 100, 20);
			        frame.getContentPane().add(lblNewLabel_6);
			        courseIdField = new JTextField();
			        courseIdField.setBounds(410, 20, 150, 20);
			        frame.getContentPane().add(courseIdField);
			        courseIdField.setColumns(10);

			        JLabel lblNewLabel_7 = new JLabel("Course Name:");
			        lblNewLabel_7.setBounds(300, 50, 100, 20);
			        frame.getContentPane().add(lblNewLabel_7);
			        courseNameField = new JTextField();
			        courseNameField.setBounds(410, 50, 150, 20);
			        frame.getContentPane().add(courseNameField);
			        courseNameField.setColumns(10);

			        JLabel lblNewLabel_8 = new JLabel("Teacher ID:");
			        lblNewLabel_8.setBounds(300, 80, 100, 20);
			        frame.getContentPane().add(lblNewLabel_8);
			        teacherIdField = new JTextField();
			        teacherIdField.setBounds(410, 80, 150, 20);
			        frame.getContentPane().add(teacherIdField);
			        teacherIdField.setColumns(10);

			        JLabel lblNewLabel_9 = new JLabel("Credit:");
			        lblNewLabel_9.setBounds(300, 110, 100, 20);
			        frame.getContentPane().add(lblNewLabel_9);
			        creditField = new JTextField();
			        creditField.setBounds(410, 110, 150, 20);
			        frame.getContentPane().add(creditField);
			        creditField.setColumns(10);

			        JLabel lblNewLabel_10 = new JLabel("Grade:");
			        lblNewLabel_10.setBounds(300, 140, 100, 20);
			        frame.getContentPane().add(lblNewLabel_10);
			        gradeField = new JTextField();
			        gradeField.setBounds(410, 140, 150, 20);
			        frame.getContentPane().add(gradeField);
			        gradeField.setColumns(10);
			        
			        JLabel lblNewLabel_11 = new JLabel("Canceled Year:");
			        lblNewLabel_11.setBounds(300, 170, 100, 20);
			        frame.getContentPane().add(lblNewLabel_11);
			        canceledYearField = new JTextField();
			        canceledYearField.setBounds(410, 170, 150, 20);
			        frame.getContentPane().add(canceledYearField);
			        canceledYearField.setColumns(10);
			        
			        //teacher
			        JLabel lblNewLabel_12 = new JLabel("Teacher ID:");
			        lblNewLabel_12.setBounds(20, 280, 100, 20);
			        frame.getContentPane().add(lblNewLabel_12);
			        teacherIdField = new JTextField();
			        teacherIdField.setBounds(130, 280, 150, 20);
			        frame.getContentPane().add(teacherIdField);
			        teacherIdField.setColumns(10);

			        JLabel lblNewLabel_13 = new JLabel("Teacher Name:");
			        lblNewLabel_13.setBounds(20, 310, 100, 20);
			        frame.getContentPane().add(lblNewLabel_13);
			        teacherNameField = new JTextField();
			        teacherNameField.setBounds(130, 310, 150, 20);
			        frame.getContentPane().add(teacherNameField);
			        teacherNameField.setColumns(10);

			        JLabel lblNewLabel_14 = new JLabel("Course:");
			        lblNewLabel_14.setBounds(20, 340, 100, 20);
			        frame.getContentPane().add(lblNewLabel_14);
			        coursesField = new JTextField();
			        coursesField.setBounds(130, 340, 150, 20);
			        frame.getContentPane().add(coursesField);
			        coursesField.setColumns(10);
			        
			        //course choosing
			        JLabel lblNewLabel_15 = new JLabel("Student ID:");
			        lblNewLabel_15.setBounds(300, 275, 100, 20);
			        frame.getContentPane().add(lblNewLabel_15);
			        studentIdField1 = new JTextField();
			        studentIdField1.setBounds(410, 275, 150, 20);
			        frame.getContentPane().add(studentIdField1);
			        studentIdField1.setColumns(10);

			        JLabel lblNewLabel_16 = new JLabel("Course ID:");
			        lblNewLabel_16.setBounds(300, 300, 100, 20);
			        frame.getContentPane().add(lblNewLabel_16);
			        courseChoosingIdField = new JTextField();
			        courseChoosingIdField.setBounds(410, 300, 150, 20);
			        frame.getContentPane().add(courseChoosingIdField);
			        courseChoosingIdField.setColumns(10);

			        JLabel lblNewLabel_17 = new JLabel("Teacher ID:");
			        lblNewLabel_17.setBounds(300, 325, 120, 20);
			        frame.getContentPane().add(lblNewLabel_17);
			        teacherChoosingIdField = new JTextField();
			        teacherChoosingIdField.setBounds(410, 325, 150, 20);
			        frame.getContentPane().add(teacherChoosingIdField);
			        teacherChoosingIdField.setColumns(10);

			        JLabel lblNewLabel_18 = new JLabel("Chosen Year:");
			        lblNewLabel_18.setBounds(300, 350, 100, 20);
			        frame.getContentPane().add(lblNewLabel_18);
			        chosenYearField = new JTextField();
			        chosenYearField.setBounds(410, 350, 150, 20);
			        frame.getContentPane().add(chosenYearField);
			        chosenYearField.setColumns(10);

			        JLabel lblNewLabel_19 = new JLabel("Score:");
			        lblNewLabel_19.setBounds(300, 375, 100, 20);
			        frame.getContentPane().add(lblNewLabel_19);
			        scoreField = new JTextField();
			        scoreField.setBounds(410, 375, 150, 20);
			        frame.getContentPane().add(scoreField);
			        scoreField.setColumns(10);
			        //login
			        
			        
			        //button
			        addStudentButton = new JButton("Add Student");
			        addStudentButton.setBounds(20, 210, 150, 30);
			        frame.getContentPane().add(addStudentButton);
			        
			        addCourseButton = new JButton("Add Course");
			        addCourseButton.setBounds(300, 210, 150, 30);
			        frame.getContentPane().add(addCourseButton);

			        //searchCourseButton = new JButton("Search Course");
			        //searchCourseButton.setBounds(460, 210, 150, 30);
			        //frame.getContentPane().add(searchCourseButton);
			        
			        addCourseChoosingButton = new JButton("Add Course Choosing");
			        addCourseChoosingButton.setBounds(375, 400, 200, 30);
			        frame.getContentPane().add(addCourseChoosingButton);

			        addTeacherButton = new JButton("Add Teacher");
			        addTeacherButton.setBounds(20, 370, 150, 30);
			        frame.getContentPane().add(addTeacherButton);
			        //color change
			        changeColorButton = new JButton("Change Color");
			        changeColorButton.setBounds(480, 670, 120, 30);
			        frame.getContentPane().add(changeColorButton);
			      
			       
			        //result
			        resultTextArea = new JTextArea();
			        resultTextArea.setBounds(50, 450, 540, 200);
			        resultTextArea.setEditable(false);
			        frame.getContentPane().add(resultTextArea);
			        

			       
			        
	addStudentButton.addActionListener(new ActionListener() {
			            @Override
			            public void actionPerformed(ActionEvent e) {
			                addStudent();
			            }
			        });
			        
	addCourseButton.addActionListener(new ActionListener() {
			            @Override
			            public void actionPerformed(ActionEvent e) {
			                addCourse();
			            }
			        });
/*
	searchCourseButton.addActionListener(new ActionListener() {
			            @Override
			            public void actionPerformed(ActionEvent e) {
			                searchCourse();
			            }
			        });
			*/        
	addTeacherButton.addActionListener(new ActionListener() {
			            @Override
			            public void actionPerformed(ActionEvent e) {
			                addTeacher();
			            }
			        });

	addCourseChoosingButton.addActionListener(new ActionListener() {
			            @Override
			            public void actionPerformed(ActionEvent e) {
			                addCourseChoosing();
			            }
			        });
			 
	changeColorButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
	        // Prompt the user to select a color
	        Color color = JColorChooser.showDialog(frame, "Select a Color", Color.WHITE);
	        
	        // Set the background color of the frame
	        if (color != null) {
	            frame.getContentPane().setBackground(color);
	        }
	    }
	});
    JButton AdminButton = new JButton("Admin Setup");
    AdminButton.setBounds(345, 670, 125, 30);
    frame.getContentPane().add(AdminButton);
    AdminButton.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
           frame.getContentPane().removeAll();
           frame.dispose();
           AdminSetup adminSetup = new AdminSetup();
       }
   });
    JButton DatabaseButton = new JButton("DataBase Enter");
    DatabaseButton.setBounds(210, 670, 125, 30);
    frame.getContentPane().add(DatabaseButton);
    DatabaseButton.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
           frame.getContentPane().removeAll();
           frame.dispose();
           MyDatabase MYdb = new MyDatabase();
       }
   });
    JButton LogoutButton = new JButton("LogOut");
    LogoutButton.setBounds(20, 670, 80, 30);
    frame.getContentPane().add(LogoutButton);
    LogoutButton.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
           frame.getContentPane().removeAll();
           frame.dispose();
           Login login = new Login();
       }
   });
		frame.setVisible(true);
			    
	}

		    
	private void initializeDatabase() {
			        try {
			            Class.forName("com.mysql.cj.jdbc.Driver");
			            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/college_db", "root", "Ssd@#1234567890");
			        } catch (ClassNotFoundException | SQLException e) {
			            e.printStackTrace();
			            JOptionPane.showMessageDialog(frame, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
			        }
			    }
			    
	private void addStudent() {
			        String studentId = studentIdField.getText();
			        String studentName = studentNameField.getText();
			        String sex = sexComboBox.getSelectedItem().toString();
			        int entranceAge = Integer.parseInt(entranceAgeField.getText());
			        int entranceYear = Integer.parseInt(entranceYearField.getText());
			        String studentClass = classField.getText();

			        try {
			            PreparedStatement statement = connection.prepareStatement("INSERT INTO students (id, name, sex, entrance_age, entrance_year, class) VALUES (?, ?, ?, ?, ?, ?)");
			            statement.setString(1, studentId);
			            statement.setString(2, studentName);
			            statement.setString(3, sex);
			            statement.setInt(4, entranceAge);
			            statement.setInt(5, entranceYear);
			            statement.setString(6, studentClass);
			            statement.executeUpdate();

			            resultTextArea.setText("Student added successfully.");
			        } catch (SQLException ex) {
			            ex.printStackTrace();
			            resultTextArea.setText("Error occurred while adding student.");
			        }
			    }
		  	    
	private void addCourse() {
	        String courseId = courseIdField.getText();
	        String courseName = courseNameField.getText();
	        String teacherId = teacherIdField.getText();
	        
	        int credit = Integer.parseInt(creditField.getText());
	        int grade = Integer.parseInt(gradeField.getText());
	        int canceledYear = canceledYearField.getText().isEmpty() ? 0 : Integer.parseInt(canceledYearField.getText());
	        
	        try {
	            PreparedStatement statement = connection.prepareStatement("INSERT INTO courses (id, name, teacher_id, credit, grade,canceled_year) VALUES (?, ?, ?, ?, ?,?)");
	            statement.setString(1, courseId);
	            statement.setString(2, courseName);
	            statement.setString(3, teacherId);
	            statement.setInt(4, credit);
	            statement.setInt(5, grade);
	            statement.setInt(6, canceledYear);
	            statement.executeUpdate();

	            resultTextArea.setText("Course added successfully.");
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            resultTextArea.setText("Error occurred while adding course.");
	        }
	    }

	  /*  
	private void searchCourse() {
	    String courseId = courseIdField.getText();
	    String courseName = courseNameField.getText();

	    try {
	        PreparedStatement statement;
	        if (!courseId.isEmpty() && !courseName.isEmpty()) {
	            statement = connection.prepareStatement("SELECT * FROM courses WHERE id = ? AND name = ?");
	            statement.setString(1, courseId);
	            statement.setString(2, courseName);
	        } else if (!courseId.isEmpty()) {
	            statement = connection.prepareStatement("SELECT * FROM courses WHERE id = ?");
	            statement.setString(1, courseId);
	        } else if (!courseName.isEmpty()) {
	            statement = connection.prepareStatement("SELECT * FROM courses WHERE name = ?");
	            statement.setString(1, courseName);
	        } else {
	            statement = connection.prepareStatement("SELECT * FROM courses");
	        }

	        // Execute the query
	        ResultSet resultSet = statement.executeQuery();

	        // Process the results
	        StringBuilder result = new StringBuilder();
	        while (resultSet.next()) {
	            String resultLine = "Course ID: " + resultSet.getString("course_id")
	                    + ", Course Name: " + resultSet.getString("course_name")
	                    + ", Teacher ID: " + resultSet.getString("teacher_id")
	                    + ", Credit: " + resultSet.getInt("credit")
	                    + ", Grade: " + resultSet.getInt("grade")
	                    + "\n";
	            result.append(resultLine);
	        }

	        if (result.length() > 0) {
	            resultTextArea.setText(result.toString());
	        } else {
	            resultTextArea.setText("No matching courses found.");
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        resultTextArea.setText("Error occurred while searching for course.");
	    }
	}
*/      
	    
	private void addTeacher() {
	        String teacherId = teacherIdField.getText();
	        String teacherName = teacherNameField.getText();
	        String courses = coursesField.getText();

	        try {
	            PreparedStatement statement = connection.prepareStatement("INSERT INTO teachers (id, name, courses) VALUES (?, ?, ?)");
	            statement.setString(1, teacherId);
	            statement.setString(2, teacherName);
	            statement.setString(3, courses);
	            statement.executeUpdate();

	            resultTextArea.setText("Teacher added successfully.");
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            resultTextArea.setText("Error occurred while adding teacher.");
	        }
	    }
	    
	private void addCourseChoosing() {
	    String studentId = studentIdField.getText();
	    String courseChoosingId = courseChoosingIdField.getText();
	    String teacherChoosingId = teacherChoosingIdField.getText();
	    int chosenYear = Integer.parseInt(chosenYearField.getText());
	    int score = Integer.parseInt(scoreField.getText());

	    try {
	        PreparedStatement statement = connection.prepareStatement("INSERT INTO course_choosing (student_id, course_id, teacher_id, chosen_year, score) VALUES (?, ?, ?, ?, ?)");
	        statement.setString(1, studentId);
	        statement.setString(2, courseChoosingId);
	        statement.setString(3, teacherChoosingId);
	        statement.setInt(4, chosenYear);
	        statement.setInt(5, score);
	        statement.executeUpdate();

	        resultTextArea.setText("Course choosing added successfully.");
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        resultTextArea.setText("Error occurred while adding course choosing.");
	    }
	}
	}
