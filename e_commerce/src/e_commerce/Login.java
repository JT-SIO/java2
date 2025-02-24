package e_commerce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class Login {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Page de Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel, BorderLayout.CENTER);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelUser = new JLabel("Nom d'utilisateur :");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(labelUser, constraints);

        JTextField textFieldUser = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(textFieldUser, constraints);

        JLabel labelPassword = new JLabel("Mot de passe :");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(labelPassword, constraints);

        JPasswordField passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(passwordField, constraints);

        JButton loginButton = new JButton("Se connecter");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(loginButton, constraints);

        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        frame.add(messageLabel, BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textFieldUser.getText();
                String password = new String(passwordField.getPassword());

                String name = authentifierUtilisateur(username, password);
                if (name != null) {
                    frame.dispose();	
                    new Dashboard(name);
                } else {
                    messageLabel.setText("Nom d'utilisateur ou mot de passe incorrect.");
                    messageLabel.setForeground(Color.RED);
                }
            }
        });

        frame.setVisible(true);
    }

    private static String authentifierUtilisateur(String username, String password) {
        try (Connection connexion = Connectiondb.getConnection()) {
            System.out.println("Connexion à la base de données réussie.");

            String query = "SELECT username, password FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setString(1, username);
            System.out.println("Requête préparée : " + query);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("username");
                String hashedPasswordInDB = resultSet.getString("password");
                System.out.println("Hash dans la base : " + hashedPasswordInDB);

                if (hashedPasswordInDB.startsWith("$2y$")) {
                    hashedPasswordInDB = hashedPasswordInDB.replaceFirst("\\$2y\\$", "\\$2a\\$");
                }

                if (BCrypt.checkpw(password, hashedPasswordInDB)) {
                    return name;
                } else {
                    System.out.println("Le mot de passe est incorrect.");
                }
            } else {
                System.out.println("Utilisateur non trouvé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données.");
            e.printStackTrace();
        }
        return null;
    }
}
