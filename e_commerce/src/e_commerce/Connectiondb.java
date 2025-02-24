package e_commerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectiondb {
    private static final String URL = "jdbc:mysql://localhost:3306/e-commerce2"; 
    private static final String UTILISATEUR = "root"; 
    private static final String MOT_DE_PASSE = "123"; 

    public static void main(String[] args) {
        Connection connexion = null; 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connexion = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
            System.out.println("Connexion réussie !");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC introuvable. Assurez-vous qu'il est ajouté à votre projet.");
            e.printStackTrace(); 
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données.");
            e.printStackTrace();
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                    System.out.println("Connexion fermée.");
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture de la connexion.");
                    e.printStackTrace();
                }
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
    }
}
