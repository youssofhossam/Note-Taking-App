import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JFrame {

    public WelcomeScreen() {
        setUndecorated(true);
        setSize(500, 300);
        setLocationRelativeTo(null);
        JPanel splashPanel = new JPanel();
        splashPanel.setBackground(new Color(173, 216, 130)); // Light green color
        splashPanel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("WELCOME", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 40)); // Large, bold font
        welcomeLabel.setForeground(Color.WHITE); // White text color

        splashPanel.add(welcomeLabel, BorderLayout.CENTER);
        add(splashPanel);

        setVisible(true);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dispose();
        new LoginScreen();
    }


}