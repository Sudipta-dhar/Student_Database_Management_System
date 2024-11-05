package DATABASE_EXP2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Registration {
	private JFrame frame;
	  private JButton registerButton;
    private Connection connection;
    
	public Registration() {
		new Main();
		GUI2();
		connectToDatabase();
	};
	private void GUI2() { 
		 frame = new JFrame("Select Your Registration Method");
		   frame.setBounds(100, 900, 500, 400);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().setLayout(null);
	        frame.setLocationRelativeTo(null);
	        frame.setResizable(false);;
         
	        //button
	        registerButton = new JButton(" Student Registration ");
	        registerButton.setBounds(150, 100, 200, 40);
	        frame.getContentPane().add(registerButton);
	        registerButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	 frame.dispose();
	            	ST_Registration registration1 = new ST_Registration();
	            }
	        });
	        registerButton = new JButton("Teacher Registration");
	        registerButton.setBounds(150, 180, 200, 40);
	        frame.getContentPane().add(registerButton);
	        registerButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	frame.dispose();
	            	T_Registration registration2 = new T_Registration();
	            }
	        });
	        
	         JButton backButton = new JButton("Back to login");
	         backButton.setBounds(175, 240, 150, 30);
	         frame.getContentPane().add(backButton);
	         backButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                frame.getContentPane().removeAll();
	                frame.dispose();
	                Login login = new Login();
	            }
	        });
	        frame.setVisible(true);
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

}