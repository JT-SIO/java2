package e_commerce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {
    private JLabel timerLabel;
    private int timeRemaining = 3600;
    private Timer timer;

    public Dashboard(String username) {
        setTitle("Espace Client - " + username);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        JLabel welcomeLabel = new JLabel("Bienvenue, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(welcomeLabel);

        timerLabel = new JLabel("Temps restant : 60:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(timerLabel);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                int minutes = timeRemaining / 60;
                int seconds = timeRemaining % 60;
                timerLabel.setText(String.format("Temps restant : %02d:%02d", minutes, seconds));

                if (timeRemaining <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(Dashboard.this, "Temps écoulé, vous serez déconnecté.");
                    System.exit(0);
                }
            }
        });
        timer.start();

        setVisible(true);
    }
}

