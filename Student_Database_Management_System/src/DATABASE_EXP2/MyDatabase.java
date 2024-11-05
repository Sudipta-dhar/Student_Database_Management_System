package DATABASE_EXP2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MyDatabase {
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JTable studentTable ,teacherTable, courseTable , courseChooseTable;
    private DefaultTableModel studentTabModel, teacherTableModel, courseTableModel, courseChooseTableModel;
    private JTextField studentidField, courseidField, teacheridField,ChosenyearField,scoreField;
    private JTextField studentIdField,studentNameField,sexField,entranceAgeField,entranceYearField,classNameField;
    private JTextField teacherIdField, teacherNameField, teacherCourseField; 
    private JTextField courseIdField, courseNameField, creditField, gradeField, canceledYearField;
    private JButton addStudentButton,modifyStudentButton,deleteStudentButton,addTeacherButton, modifyTeacherButton, deleteTeacherButton, addCourseButton, modifyCourseButton, deleteCourseButton;
    private JButton addcoursechooseButton,modifycoursechooseButton,deletecoursechooseButton;
    private Connection connection;
    private JButton BackButton;
    
    
    public MyDatabase() {
        initializeUI();
        connectToDatabase();
        loadTeacherData();
        loadCourseData();
        loadStudentData();
        loadCousreChooseData();
    }

    private void initializeUI() {
        frame = new JFrame("Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        tabbedPane = new JTabbedPane();

        //Student Panel
        JPanel studentPanel = createStudentPanel();
        tabbedPane.addTab("Students", studentPanel);
        
        // Teacher Panel
        JPanel teacherPanel = createTeacherPanel();
        tabbedPane.addTab("Teachers", teacherPanel);

        // Course Panel
        JPanel coursePanel = createCoursePanel();
        tabbedPane.addTab("Courses", coursePanel);
        
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
                Admin admin = new Admin();
            }
        });

        frame.getContentPane().add(tabbedPane);
        frame.setVisible(true);
    }
    //Create student Panel
    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel formPanel = createStudentFormPanel();
        panel.add(formPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        studentTabModel = new DefaultTableModel(new Object[]{"id", "name", "sex","entrance_age","entrance_year","class"}, 0);
        studentTable = new JTable(studentTabModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow != -1) {
                String studentId = (String) studentTabModel.getValueAt(selectedRow, 0);
                String studentName = (String) studentTabModel.getValueAt(selectedRow, 1);
                String sex = (String) studentTabModel.getValueAt(selectedRow, 2);
                String entranceAge = (String) studentTabModel.getValueAt(selectedRow, 3);
                String entranceYear = (String) studentTabModel.getValueAt(selectedRow, 4);
                String className = (String) studentTabModel.getValueAt(selectedRow, 5);
       
                studentIdField.setText(studentId);
                studentNameField.setText(studentName);
                sexField.setText(sex);
                entranceAgeField.setText(entranceAge);
                entranceYearField.setText(entranceYear);
                classNameField.setText(className);
            }
        });
        scrollPane.setViewportView(studentTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = createStudentButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
    
    private JPanel createStudentFormPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2));

        JLabel studentIdLabel = new JLabel("ID:");
        studentIdField = new JTextField();
        panel.add(studentIdLabel);
        panel.add(studentIdField);

        JLabel studentNameLabel = new JLabel("Name:");
        studentNameField = new JTextField();
        panel.add(studentNameLabel);
        panel.add(studentNameField);

        JLabel studentsexLabel = new JLabel("Sex:");
        sexField = new JTextField();
        panel.add(studentsexLabel);
        panel.add(sexField);

        
        JLabel studentageLabel = new JLabel("Entrance Age:");
        entranceAgeField = new JTextField();
        panel.add(studentageLabel);
        panel.add(entranceAgeField);
        
        JLabel studentyearLabel = new JLabel("Entrance Year:");
        entranceYearField = new JTextField();
        panel.add(studentyearLabel);
        panel.add(entranceYearField);
        
        JLabel studentclassLabel = new JLabel("Class:");
        classNameField = new JTextField();
        panel.add(studentclassLabel);
        panel.add(classNameField);
        
        return panel;
    }
    
    private JPanel createStudentButtonPanel() {
        JPanel panel = new JPanel();

        addStudentButton = new JButton("Add Student");
        addStudentButton.addActionListener(e -> addStudent());
        panel.add(addStudentButton);

        modifyStudentButton = new JButton("Modify Student");
        modifyStudentButton.addActionListener(e -> modifyStudent());
        panel.add(modifyStudentButton);

        deleteStudentButton = new JButton("Delete Student");
        deleteStudentButton.addActionListener(e -> deleteStudent());
        panel.add(deleteStudentButton);
        
        return panel;
    }
    
    //Create teacher Panel
    private JPanel createTeacherPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel formPanel = createTeacherFormPanel();
        panel.add(formPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        teacherTableModel = new DefaultTableModel(new Object[]{"id", "name", "courses"}, 0);
        teacherTable = new JTable(teacherTableModel);
        teacherTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        teacherTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = teacherTable.getSelectedRow();
            if (selectedRow != -1) {
                String teacherId = (String) teacherTableModel.getValueAt(selectedRow, 0);
                String teacherName = (String) teacherTableModel.getValueAt(selectedRow, 1);
                String teacherCourse = (String) teacherTableModel.getValueAt(selectedRow, 2);

                teacherIdField.setText(teacherId);
                teacherNameField.setText(teacherName);
                teacherCourseField.setText(teacherCourse);
            }
        });
        scrollPane.setViewportView(teacherTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = createTeacherButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createTeacherFormPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel teacherIdLabel = new JLabel("ID:");
        teacherIdField = new JTextField();
        panel.add(teacherIdLabel);
        panel.add(teacherIdField);

        JLabel teacherNameLabel = new JLabel("Name:");
        teacherNameField = new JTextField();
        panel.add(teacherNameLabel);
        panel.add(teacherNameField);

        JLabel teacherCourseLabel = new JLabel("Course:");
        teacherCourseField = new JTextField();
        panel.add(teacherCourseLabel);
        panel.add(teacherCourseField);

        return panel;
    }

    private JPanel createTeacherButtonPanel() {
        JPanel panel = new JPanel();

        addTeacherButton = new JButton("Add Teacher");
        addTeacherButton.addActionListener(e -> addTeacher());
        panel.add(addTeacherButton);

        modifyTeacherButton = new JButton("Modify Teacher");
        modifyTeacherButton.addActionListener(e -> modifyTeacher());
        panel.add(modifyTeacherButton);

        deleteTeacherButton = new JButton("Delete Teacher");
        deleteTeacherButton.addActionListener(e -> deleteTeacher());
        panel.add(deleteTeacherButton);

        return panel;
    }
    
    //Create course Panel
    private JPanel createCoursePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel formPanel = createCourseFormPanel();
        panel.add(formPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane();
        courseTableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Teacher ID", "Credit", "Grade", "Canceled Year"}, 0);
        courseTable = new JTable(courseTableModel);
        courseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow != -1) {
                String courseId = (String) courseTableModel.getValueAt(selectedRow, 0);
                String courseName = (String) courseTableModel.getValueAt(selectedRow, 1);
                String teacherId = (String) courseTableModel.getValueAt(selectedRow, 2);
                int credit = (int) courseTableModel.getValueAt(selectedRow, 3);
                int grade = (int) courseTableModel.getValueAt(selectedRow, 4);
                int canceledYear = (int) courseTableModel.getValueAt(selectedRow, 5);

                courseIdField.setText(courseId);
                courseNameField.setText(courseName);
                teacherIdField.setText(teacherId);
                creditField.setText(String.valueOf(credit));
                gradeField.setText(String.valueOf(grade));
                canceledYearField.setText(String.valueOf(canceledYear));
            }
        });
        scrollPane.setViewportView(courseTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = createCourseButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createCourseFormPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2));

        JLabel courseIdLabel = new JLabel("ID:");
        courseIdField = new JTextField();
        panel.add(courseIdLabel);
        panel.add(courseIdField);

        JLabel courseNameLabel = new JLabel("Name:");
        courseNameField = new JTextField();
        panel.add(courseNameLabel);
        panel.add(courseNameField);

        JLabel teacherIdLabel = new JLabel("Teacher ID:");
        teacherIdField = new JTextField();
        panel.add(teacherIdLabel);
        panel.add(teacherIdField);

        JLabel creditLabel = new JLabel("Credit:");
        creditField = new JTextField();
        panel.add(creditLabel);
        panel.add(creditField);

        JLabel gradeLabel = new JLabel("Grade:");
        gradeField = new JTextField();
        panel.add(gradeLabel);
        panel.add(gradeField);

        JLabel canceledYearLabel = new JLabel("Canceled Year:");
        canceledYearField = new JTextField();
        panel.add(canceledYearLabel);
        panel.add(canceledYearField);

        return panel;
    }

    private JPanel createCourseButtonPanel() {
        JPanel panel = new JPanel();

        addCourseButton = new JButton("Add Course");
        addCourseButton.addActionListener(e -> addCourse());
        panel.add(addCourseButton);

        modifyCourseButton = new JButton("Modify Course");
        modifyCourseButton.addActionListener(e -> modifyCourse());
        panel.add(modifyCourseButton);

        deleteCourseButton = new JButton("Delete Course");
        deleteCourseButton.addActionListener(e -> deleteCourse());
        panel.add(deleteCourseButton);

        return panel;
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

        addcoursechooseButton = new JButton("Add Course Choose");
        addcoursechooseButton.addActionListener(e -> addCourseChoose());
        panel.add(addcoursechooseButton);

        modifycoursechooseButton = new JButton("Modify Course Choose");
        modifycoursechooseButton.addActionListener(e -> modifyCourseChooseScore());
        panel.add(modifycoursechooseButton);

        deletecoursechooseButton = new JButton("Delete Course Choose");
        deletecoursechooseButton.addActionListener(e -> deleteCourseChoose());
        panel.add(deletecoursechooseButton);

        return panel;
    }
    
    //load Data
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

                studentTabModel.addRow(new Object[]{studentId, studentName, sex, entranceAge,entranceyear, className});
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while loading student data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
  }
    
    private void loadTeacherData() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM teachers");
            while (resultSet.next()) {
                String teacherId = resultSet.getString("id");
                String teacherName = resultSet.getString("name");
                String teacherCourse = resultSet.getString("courses");
                Object[] rowData = {teacherId, teacherName, teacherCourse};
                teacherTableModel.addRow(rowData);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while loading teacher data.", "Error", JOptionPane.ERROR_MESSAGE);
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
                courseTableModel.addRow(new Object[]{courseId, courseName, teacherId, credit, grade, canceledYear});
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    
    //Student
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
                studentTabModel.addRow(rowData);
                clearStudentFields();
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
            String oldStudentId = (String) studentTabModel.getValueAt(selectedRow, 0);
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
            	studentTabModel.setValueAt(studentId, selectedRow, 0);
            	studentTabModel.setValueAt(studentName, selectedRow, 1);
            	studentTabModel.setValueAt(sex, selectedRow, 2);
            	studentTabModel.setValueAt(entranceAge, selectedRow, 3);
            	studentTabModel.setValueAt(entranceyear, selectedRow, 4);
            	studentTabModel.setValueAt(className, selectedRow, 5);
            	clearStudentFields();
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

        String studentId = (String) studentTabModel.getValueAt(selectedRow, 0);

        int confirmation = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this student?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM students WHERE id=?");
                statement.setString(1, studentId);

                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                	studentTabModel.removeRow(selectedRow);
                	clearStudentFields();
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
    
    //Teacher
    private void addTeacher() {
        String teacherId = teacherIdField.getText();
        String teacherName = teacherNameField.getText();
        String teacherCourse = teacherCourseField.getText();

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO teachers (id, name, courses) VALUES (?, ?, ?)");
            statement.setString(1, teacherId);
            statement.setString(2, teacherName);
            statement.setString(3, teacherCourse);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                Object[] rowData = {teacherId, teacherName, teacherCourse};
                teacherTableModel.addRow(rowData);
                clearTeacherFields();
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
        String teacherCourse = teacherCourseField.getText();
        try {
            String oldTeacherId = (String) teacherTableModel.getValueAt(selectedRow, 0);
            PreparedStatement statement = connection.prepareStatement("UPDATE teachers SET id=?, name=?, courses=? WHERE id=?");
            statement.setString(1, teacherId);
            statement.setString(2, teacherName);
            statement.setString(3, teacherCourse);
            statement.setString(4, oldTeacherId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
            	teacherTableModel.setValueAt(teacherId, selectedRow, 0);
            	teacherTableModel.setValueAt(teacherName, selectedRow, 1);
            	teacherTableModel.setValueAt(teacherCourse, selectedRow, 2);
            	clearTeacherFields();
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

        String studentId = (String)teacherTableModel .getValueAt(selectedRow, 0);

        int confirmation = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this Teacher?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM teachers WHERE id=?");
                statement.setString(1, studentId);

                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                	teacherTableModel.removeRow(selectedRow);
                	clearTeacherFields();
                    JOptionPane.showMessageDialog(frame, "Teacher deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to delete Teacher.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error occurred while deleting Teacher.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Course
    private void addCourse() {
        String courseId = courseIdField.getText();
        String courseName = courseNameField.getText();
        String teacherId = teacherIdField.getText();
        int credit = Integer.parseInt(creditField.getText());
        int grade = Integer.parseInt(gradeField.getText());
        int canceledYear = Integer.parseInt(canceledYearField.getText());

        if (courseId.isEmpty() || courseName.isEmpty() || teacherId.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO courses (id, name, teacher_id, credit, grade, canceled_year) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, courseId);
            statement.setString(2, courseName);
            statement.setString(3, teacherId);
            statement.setInt(4, credit);
            statement.setInt(5, grade);
            statement.setInt(6, canceledYear);
            statement.executeUpdate();
            statement.close();

            courseTableModel.addRow(new Object[]{courseId, courseName, teacherId, credit, grade, canceledYear});

            clearCourseFields();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to add the course.", "Error", JOptionPane.ERROR_MESSAGE);
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

        if (courseId.isEmpty() || courseName.isEmpty() || teacherId.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE courses SET name = ?, teacher_id = ?, credit = ?, grade = ?, canceled_year = ? WHERE id = ?");
            statement.setString(1, courseName);
            statement.setString(2, teacherId);
            statement.setInt(3, credit);
            statement.setInt(4, grade);
            statement.setInt(5, canceledYear);
            statement.setString(6, courseId);
            statement.executeUpdate();
            statement.close();

            courseTableModel.setValueAt(courseId, selectedRow, 0);
            courseTableModel.setValueAt(courseName, selectedRow, 1);
            courseTableModel.setValueAt(teacherId, selectedRow, 2);
            courseTableModel.setValueAt(credit, selectedRow, 3);
            courseTableModel.setValueAt(grade, selectedRow, 4);
            courseTableModel.setValueAt(canceledYear, selectedRow, 5);

            clearCourseFields();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to modify the course.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCourse() {
        int selectedRow = courseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a Course to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String coursId = (String) courseTableModel.getValueAt(selectedRow, 0);

        int confirmation = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this Course?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM courses WHERE id=?");
                statement.setString(1, coursId);

                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                	courseTableModel.removeRow(selectedRow);
                	clearCourseFields();
                    JOptionPane.showMessageDialog(frame, "Course deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to delete Course.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error occurred while deleting Course.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //Course Choose
    public void addCourseChoose() {
        String studentid = studentidField.getText();
        String courseid = courseidField.getText();
        String teacherid = teacheridField.getText();
        String ChosenYear = ChosenyearField.getText();
        String score= scoreField.getText();
       

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO course_choosing (student_id, course_id, teacher_id, chosen_year, score ) VALUES (?, ?, ?, ?, ? )");
            statement.setString(1, studentid);
            statement.setString(2, courseid);
            statement.setString(3, teacherid);
            statement.setString(4, ChosenYear);
            statement.setString(5, score);


            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                Object[] rowData = {studentid, courseid, teacherid, ChosenYear, score };
                courseChooseTableModel.addRow(rowData);
                clearStudentFields();
                JOptionPane.showMessageDialog(frame, " Course Choose added successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to add Course Choose.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error occurred while adding Course Choose.", "Error", JOptionPane.ERROR_MESSAGE);
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

    public void deleteCourseChoose() {
        int selectedRow = courseChooseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a Choose Course to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String score = (String) courseChooseTableModel.getValueAt(selectedRow, 0);

        int confirmation = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this Choose Course?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM course_choosing WHERE student_id=?");
                statement.setString(1, score);

                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                	courseChooseTableModel.removeRow(selectedRow);
                	clearCourseFields();
                    JOptionPane.showMessageDialog(frame, " Choose Course deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to delete Choose Course.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error occurred while deleting Choose Course.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
    //Clear Field
    private void clearStudentFields() {
        studentIdField.setText("");
        studentNameField.setText("");
        sexField.setText("");
        entranceAgeField.setText("");
        entranceYearField.setText("");
        classNameField.setText("");
    }
    
    private void clearTeacherFields() {
        teacherIdField.setText("");
        teacherNameField.setText("");
        teacherCourseField.setText("");
    }

    private void clearCourseFields() {
        courseIdField.setText("");
        courseNameField.setText("");
        teacherIdField.setText("");
        creditField.setText("");
        gradeField.setText("");
        canceledYearField.setText("");
    }
    private void clearCourseChooseFields() {
    	studentidField.setText("");
    	courseidField.setText("");
    	teacheridField.setText("");
    	ChosenyearField.setText("");
    	scoreField.setText("");

    }

 
}

