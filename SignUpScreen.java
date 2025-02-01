import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpScreen extends JFrame implements ActionListener {
    private JPanel mainPanel;
    JButton signUpButton;
    JPanel formPanel;
    JTextField firstNameField, lastNameField, usernameField;
    JPasswordField passwordField;
    JLabel loginLabel, loginLink;
    UserManager usmg;
    User currUser;

    public SignUpScreen() {
        usmg = new UserManager();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        ImageIcon appIcon = new ImageIcon("D:/Faculty/Second Year/1st Semester/OOP/Note Taking App/icon.jpg");
        this.setIconImage(appIcon.getImage());

        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(173, 216, 130)); // Light Green
        mainPanel.setLayout(new GridBagLayout());

        formPanel = new JPanel();
        formPanel.setPreferredSize(new Dimension(300, 400));
        formPanel.setBackground(Color.WHITE);
        formPanel.setLayout(null);

        firstNameField = new JTextField();
        firstNameField.setBounds(20, 20, 260, 35);
        firstNameField.setText("First Name");
        firstNameField.setBackground(new Color(241, 238, 237));

        lastNameField = new JTextField();
        lastNameField.setBounds(20, 70, 260, 35);
        lastNameField.setText("Last Name");
        lastNameField.setBackground(new Color(241, 238, 237));

        usernameField = new JTextField();
        usernameField.setBounds(20, 120, 260, 35);
        usernameField.setText("Username");
        usernameField.setBackground(new Color(241, 238, 237));

        passwordField = new JPasswordField();
        passwordField.setBounds(20, 170, 260, 35);
        passwordField.setText("Password");
        passwordField.setBackground(new Color(241, 238, 237));

        signUpButton = new JButton("SIGN UP");
        signUpButton.setBounds(20, 270, 260, 40);
        signUpButton.setBackground(new Color(173, 216, 130));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFocusPainted(false);
        signUpButton.addActionListener(this);

        loginLabel = new JLabel("Already have an account?");
        loginLabel.setBounds(70, 330, 200, 20);

        loginLink = new JLabel("Login");
        loginLink.setBounds(230, 330, 50, 20);
        loginLink.setForeground(new Color(173, 216, 130));

        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open the Login screen
                dispose();
                new LoginScreen();

            }
        });

        formPanel.add(firstNameField);
        formPanel.add(lastNameField);
        formPanel.add(usernameField);
        formPanel.add(passwordField);
        formPanel.add(signUpButton);
        formPanel.add(loginLabel);
        formPanel.add(loginLink);

        mainPanel.add(formPanel);
        this.add(mainPanel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
            String firstNames = firstNameField.getText();
            String lastNames = lastNameField.getText();
            String usernames = usernameField.getText();
            char[] pass = passwordField.getPassword();
            String passwords = new String(pass);

            String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
            Pattern pattern = Pattern.compile(regex);

            if (!validatePassword(passwords, pattern) || (passwords == usernames)) {
                JOptionPane.showMessageDialog(null, "Password not valid", "warning", JOptionPane.INFORMATION_MESSAGE);

            } else {
                currUser = new User(firstNames, lastNames, usernames, passwords);

                if (usmg.checkValidityOfUser(currUser)) {
                    JOptionPane.showMessageDialog(null, "You have an existing account you can login", "warning", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new LoginScreen();
                } else {
                    if (usmg.checkExistanceOfUser(currUser)) {
                        JOptionPane.showMessageDialog(null, "Username already exists", "warning", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        dispose();
                        usmg.addUser(currUser);
                        new NoteApp(currUser);
                    }

                }
            }

        }
    }

    public static boolean validatePassword(String password, Pattern pattern) {
        // Match the password against the regex pattern
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
