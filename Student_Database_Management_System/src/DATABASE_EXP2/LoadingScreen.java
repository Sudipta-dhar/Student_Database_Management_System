package DATABASE_EXP2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadingScreen extends JFrame {
    private JProgressBar progressBar;
    private JLabel progressLabel;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Timer timer;

    public LoadingScreen() {
        // Set frame properties
        setSize(700, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create and set background color gradient
        JPanel contentPane = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(106, 90, 205); // Baby Pink
                Color color2 = new Color(135, 206, 250); // White
                GradientPaint gradient = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, w, h);
            }
        };
        setContentPane(contentPane);

        // Create loading bar and progress label
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressLabel = new JLabel("0%", SwingConstants.CENTER);
        progressLabel.setFont(new Font("Arial", Font.BOLD, 0));
        progressLabel.setForeground(Color.BLACK);

        // Create panel for logo and text
        JPanel TextPanel = new JPanel(new BorderLayout());
        TextPanel.setOpaque(false);

        // Add title label
        JLabel titleLabel = new JLabel("South China University of Technology");
        titleLabel.setFont(new Font("Edwardian Script ITC", Font.BOLD, 60));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        TextPanel.add(titleLabel, BorderLayout.CENTER);
        
        JLabel titleLabel1 = new JLabel( "华南理工大学");
        titleLabel1.setFont(new Font("Microsoft YaHei", Font.BOLD, 80));
        titleLabel1.setForeground(Color.CYAN);
        titleLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        TextPanel.add(titleLabel1, BorderLayout.NORTH);
        // Set layout and add components
        setLayout(new BorderLayout());
        add(progressBar, BorderLayout.SOUTH);
        add(progressLabel, BorderLayout.NORTH);

        // Create panel for card layout
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.setOpaque(false);
        cardPanel.add(TextPanel);
        add(cardPanel, BorderLayout.CENTER);

        // Create "Read Me" panel
        JPanel readmePanel = new JPanel(new BorderLayout());
        readmePanel.setOpaque(false);

        // Add instructions label
        JLabel instructionsLabel = new JLabel("Instructions on how to use the program...");
        instructionsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        instructionsLabel.setForeground(Color.BLACK);
        instructionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        readmePanel.add(instructionsLabel, BorderLayout.NORTH);

        String programDescription = "To run the program you need to add an external jar from the built path. The external jar file is provided inside the folder.\n\n" +
                "The program based on object-oriented method . There are 20 classes which work has been defined properly.\n\n" +
                "1. In the login panel, there are three types of login: Admin, Student, and Teacher.\n" +
                "2. For students and teachers, they need to register themselves for login.\n" +
                "3. In the registration for students, they need to use their original user ID and username which will be provided by the University admin. The same applies to teachers; they also need to use their original user ID and name provided by the University. Basically, for students and teachers, their information is saved in the students and teachers databases which are already added by the admin.\n" +
                "4. For the admin login, use the admin ID and password.\n" +
                "5. Inside the admin panel, we have full access to our program, such as adding, deleting, and modifying information for students, teachers, and courses. We can also choose courses.\n" +
                "6. For the student class, a student can see his own information, search student information, and see the courses and their scores using his student ID.\n" +
                "7. For teachers, after login, they can see their own information, as well as view student information and give scores to students for their courses.";
        JTextArea programDescriptionArea = new JTextArea(programDescription);
        programDescriptionArea.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        programDescriptionArea.setLineWrap(true);
        programDescriptionArea.setWrapStyleWord(true);
        programDescriptionArea.setEditable(false);
        programDescriptionArea.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(programDescriptionArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        readmePanel.add(scrollPane, BorderLayout.CENTER);

        // Add "I Understand" button
        JButton understandButton = new JButton("I Understand");
        understandButton.setBackground(new Color(70, 130, 180));
        understandButton.setForeground(Color.WHITE);
        understandButton.setFocusPainted(false);
        understandButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showLoginScreen();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(understandButton);
        readmePanel.add(buttonPanel, BorderLayout.SOUTH);
        cardPanel.add(readmePanel, "readmePanel");

        // Create and start the timer
        timer = new Timer(20, new ActionListener() {
            int progress = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (progress >= 100) {
                    timer.stop();
                    showReadMePanel();
                } else {
                    progress++;
                    progressBar.setValue(progress);
                    progressLabel.setText(progress + "%");
                }
            }
        });
        timer.start();

        // Show the loading screen
        setVisible(true);
    }

    private void showReadMePanel() {
        cardLayout.show(cardPanel, "readmePanel");
    }

    private void showLoginScreen() {
    	dispose();
        // Replace this with code to show the login panel
    	Login login = new Login(); login.setVisible(true);
    }
}
