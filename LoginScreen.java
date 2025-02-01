import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginScreen extends JFrame {

    JPanel mainPanel;
    JLabel userLabel, passLabel, registerLabel;
    JTextField userText;
    JPasswordField passText;
    JButton loginButton;
    UserManager usmgg;

    public LoginScreen() {
        usmgg = new UserManager();
        // Set JFrame properties
        setTitle("Login Screen");
        setSize(400, 300);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);
        ImageIcon appIcon = new ImageIcon("D:/Faculty/Second Year/1st Semester/OOP/Note Taking App/icon.jpg");
        this.setIconImage(appIcon.getImage());

        // Create JPanel with green background
        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(173, 216, 130)); // Light green background
        mainPanel.setLayout(null); // Absolute layout for positioning

        // Username Label
        userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 50, 80, 25);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setForeground(Color.BLACK);
        mainPanel.add(userLabel);

        // Username TextField
        userText = new JTextField(20);
        userText.setBounds(150, 50, 165, 25);
        mainPanel.add(userText);

        // Password Label
        passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 100, 80, 25);
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passLabel.setForeground(Color.BLACK);
        mainPanel.add(passLabel);

        // Password Field
        passText = new JPasswordField(20);
        passText.setBounds(150, 100, 165, 25);
        mainPanel.add(passText);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setBounds(150, 150, 80, 25);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(34, 139, 34)); // Dark green button
        mainPanel.add(loginButton);

        // Register Link
        registerLabel = new JLabel("Don't have an account? Register");
        registerLabel.setBounds(100, 200, 250, 25);
        registerLabel.setForeground(Color.BLUE);
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        mainPanel.add(registerLabel);


        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new SignUpScreen();
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                registerLabel.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerLabel.setForeground(Color.BLUE);
            }
        });


        // Action Listener for Login Button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = userText.getText();
                String password = new String(passText.getPassword());

                User currUser = new User(username, password);

                if (usmgg.checkValidityOfUser(currUser)) {
                    dispose();
                    new NoteApp(currUser);
                } else {
                    if (usmgg.checkExistanceOfUser(currUser)) {
                        JOptionPane.showMessageDialog(null, "Password incorrect", "warning", JOptionPane.INFORMATION_MESSAGE);

                    } else {
                        JOptionPane.showMessageDialog(null, "username doesnt exist", "warning", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

//                    dispose();
//                    new SignUpScreen();

            }
        });

        // Add panel to frame
        add(mainPanel);
        setVisible(true);
    }

    public static boolean validatePassword(String password, Pattern pattern) {
        // Match the password against the regex pattern
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
