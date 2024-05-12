import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class  Main {
   private static final String URL="jdbc:mysql://localhost:3306/bibliotheque";
   private static final String USERNAME = "root";
   private static final String PASSWORD = "senegalais";

   public static void Seconnecter() {
    
        Scanner scanner = new Scanner(System.in);
        
        boolean identificationReussie = false;
    
        // Boucle tant que l'identification n'est pas réussie
        while (!identificationReussie) {
            System.out.println("Veuillez saisir votre IDE (C'est la partie alphabetique de votre Carte Etudiant)\n");
            String id = scanner.nextLine();
            System.out.println("Veuillez saisir votre mot de passe\n");
            String mot = scanner.nextLine();
    
            // Tentative de connexion à la base de données
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                // Requête SQL pour vérifier si l'identifiant et le mot de passe existent
                String sql = "SELECT * FROM Utilisateurs WHERE identifiant = ? AND mot_de_passe = ?";
    
                // Préparation de la requête
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    // Attribution des valeurs d'identifiant et de mot de passe à la requête
                    statement.setString(1, id);
                    statement.setString(2, mot);
                    // Exécution de la requête
                    try (ResultSet resultSet = statement.executeQuery()) {
                        // Vérification si un résultat est retourné
                        if (resultSet.next()) {
                            System.out.println("Connexion réussie !");
                            identificationReussie = true;
    
                            Menu(connection);
    
                        } else {
                            System.out.println("Identifiant ou mot de passe incorrect. Que voulez-vous faire ?");
                            System.out.println("1. Réessayer");
                            System.out.println("2. S'inscrire");
                            System.out.println("3. Quitter");
    
                            int choix = scanner.nextInt();
                            scanner.nextLine(); 
    
                            switch (choix) {
                                case 1:
                                    
                                    break;
                                case 2:
                                    Inscription(connection);
                                    break;
                                case 3:
                                    
                                    System.exit(0);
                                default:
                                    System.out.println("Choix invalide. Veuillez réessayer.");
                                    break;
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    


public static void Menu(Connection connection) {
    Scanner sa = new Scanner(System.in);
    int rep;

    while (true) {
        System.out.println("\t         Menu");
        System.out.println("\t Que voulez faire: ");
        System.out.println("\t 1. Emprunter un Livre");
        System.out.println("\t 2. Retourner un livre");
        System.out.println("\t 3. Voir ces emprunts");
        System.out.println("\t 4. Quitter");

        if (sa.hasNextInt()) {
            rep = sa.nextInt();
            switch (rep) {


        case 1:

        // Requête SQL pour récupérer tous les livres disponibles avec leurs auteurs et dates de publication
String sql = "SELECT titre, auteur, annee_publication, disponible FROM Livres WHERE disponible = TRUE";

try (PreparedStatement statement = connection.prepareStatement(sql);
     ResultSet resultSet = statement.executeQuery()) {
    System.out.println("Liste des livres disponibles :");
    while (resultSet.next()) {
        String titre = resultSet.getString("titre");
        String auteur = resultSet.getString("auteur");
        int anneePublication = resultSet.getInt("annee_publication");
        boolean disponible = resultSet.getBoolean("disponible");
        String disponibilite = disponible ? "Disponible" : "Indisponible";
        System.out.println("- " + titre + " par " + auteur + " (" + anneePublication + ") - " + disponibilite);
    }
} catch (SQLException e) {
    e.printStackTrace();
    break; // Sortir du switch en cas d'erreur SQL
}

    
    
        // Demander à l'utilisateur de choisir un livre
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Veuillez saisir votre identifiant :");
        String identifiant = sa.next();
        System.out.println("Vous pouvez emprunter qu'un livre à la fois");
        System.out.println("Veuillez saisir le titre du livre que vous souhaitez emprunter :");
        String titreChoisi = scanner1.nextLine();
    
        // Mettre à jour la disponibilité du livre choisi dans la base de données
        String updateSql = "UPDATE Livres SET disponible = FALSE WHERE titre = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
            updateStatement.setString(1, titreChoisi);
            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Vous avez emprunté le livre : " + titreChoisi);
                System.out.println("Vous avez un delai d'une semaine pour rendre ce livre");
    
                // Mettre à jour la variable Livre_emprunte de l'utilisateur dans la table Utilisateurs
                String updateLivreEmprunteSql = "UPDATE Utilisateurs SET Livre_emprunte = ? WHERE Identifiant = ?";
                try (PreparedStatement updateLivreEmprunteStatement = connection.prepareStatement(updateLivreEmprunteSql)) {
                    updateLivreEmprunteStatement.setString(1, titreChoisi);
                    updateLivreEmprunteStatement.setString(2, identifiant); // Assurez-vous d'avoir l'identifiant de l'utilisateur
                    updateLivreEmprunteStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    break; // Sortir du switch en cas d'erreur SQL
                }
            } else {
                System.out.println("Le livre n'est pas disponible ou n'existe pas !");
                break; // Sortir du switch si le livre n'est pas disponible
            }
        } catch (SQLException e) {
            e.printStackTrace();
            break; // Sortir du switch en cas d'erreur SQL
        }
    
        break;
    


    case 2:
    // Demander à l'utilisateur de saisir son identifiant
    System.out.println("Veuillez saisir votre identifiant :");
    String identifiant2 = sa.next();

    sa.nextLine();

    // Demander à l'utilisateur de saisir le titre du livre qu'il souhaite retourner
    System.out.println("Veuillez saisir le titre du livre que vous souhaitez retourner :");
    String titreLivre = sa.nextLine();


    // Mettre à jour la disponibilité du livre dans la table Livres
    String updateSqll = "UPDATE Livres SET disponible = TRUE WHERE titre = ?";
    try (PreparedStatement updateStatement = connection.prepareStatement(updateSqll)) {
        updateStatement.setString(1, titreLivre);
        int rowsAffected = updateStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Vous avez retourné le livre : " + titreLivre);

            // Mettre à jour la variable Livre_emprunte de l'utilisateur dans la table Utilisateurs
            String updateLivreEmprunteSql = "UPDATE Utilisateurs SET Livre_emprunte = NULL WHERE Identifiant = ?";
            try (PreparedStatement updateLivreEmprunteStatement = connection.prepareStatement(updateLivreEmprunteSql)) {
                updateLivreEmprunteStatement.setString(1, identifiant2);
                updateLivreEmprunteStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                break; // Sortir du switch en cas d'erreur SQL
            }
        } else {
            System.out.println("Vous n'avez pas emprunté ce livre !");
            break; // Sortir du switch si le livre n'est pas emprunté
        }
    } catch (SQLException e) {
        e.printStackTrace();
        break; // Sortir du switch en cas d'erreur SQL
    }

    break;

                
                    case 3:
                    // Demander l'identifiant de l'utilisateur
                    Scanner scanner3 = new Scanner(System.in);
                    System.out.println("Veuillez saisir votre identifiant :");
                    String identifiant3 = scanner3.nextLine();
                
                    // Requête SQL pour récupérer les livres empruntés par l'utilisateur
                    String sqll = "SELECT Livre_emprunte FROM Utilisateurs WHERE Identifiant = ?";
                    try (PreparedStatement statement = connection.prepareStatement(sqll)) {
                        statement.setString(1, identifiant3);
                        ResultSet resultSet = statement.executeQuery();
                        if (resultSet.next()) {
                            String livresEmpruntes = resultSet.getString("Livre_emprunte");
                            if (livresEmpruntes != null) {
                                System.out.println("Livres empruntés : " + livresEmpruntes);
                            } else {
                                System.out.println("Vous n'avez emprunté aucun livre.");
                            }
                        } else {
                            System.out.println("Identifiant incorrect !");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        break; // Sortir du switch en cas d'erreur SQL
                    }
                
                    break;
                
                 case 4:
                    System.out.println("Vous avez choisi de quitter. Au revoir !");
                    return;
                default:
                    System.out.println("Choix invalide !");
                    break;
                }
        } else {
            System.out.println("Erreur! Veuillez saisir un entier.");
            sa.next(); // Pour vider le scanner
        }
    }

 
 }

     
 public static void Inscription(Connection connection) {
    Scanner sb = new Scanner(System.in);
    char reponse;

    do {
        System.out.println("Veuillez donnez votre nom:\n");
        String nom = sb.nextLine();

        System.out.println("Veuillez donner votre prénom : \n");
        String prenom = sb.nextLine();

        System.out.println("Veuillez choisir votre genre: HOMME, FEMME\n");
        String sexe = sb.nextLine().toUpperCase();

        System.out.print("Veuillez saisir la partie alphabétique de votre numéro de carte d'étudiant (Cela correspond à votre IDE)\n");
        String ide = sb.nextLine();

        System.out.println("Veuillez saisir votre mot de passe (il doit être lettre seulement)");
        String mot = sb.nextLine();
       
        


        Utilisateur utilisateur = new Utilisateur(nom, prenom, ide, mot, EnumSexe.valueOf(sexe));
        

        System.out.println("\n======================VERIFIEZ S'IL Y A PAS D'ERREUR=========================");
        System.out.println("\nNom:" + utilisateur.getNom());
        System.out.println("\nPrenom:" + utilisateur.getPrenom());
        System.out.println("\nVotre IDE:" + utilisateur.getIDE());
        System.out.println("\nVotre Mot de passe:" + utilisateur.getPassword());
        System.out.println("\nVotre genre:" + utilisateur.getSexe());

        System.out.println("Si tout est OK tapes O sinon tapes N");
        reponse = sb.nextLine().charAt(0);

        // Insérez l'utilisateur dans la base de données sans la colonne jour_inscription
        if (reponse == 'O') {
            try {
                String requete = "INSERT INTO Utilisateurs (nom, prenom, sexe, identifiant, mot_de_passe) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(requete);
                preparedStatement.setString(1, utilisateur.getNom());
                preparedStatement.setString(2, utilisateur.getPrenom());
                preparedStatement.setString(3, utilisateur.getSexe().toString());
                preparedStatement.setString(4, utilisateur.getIDE());
                preparedStatement.setString(5, utilisateur.getPassword());
                

                preparedStatement.executeUpdate();
                System.out.println("Inscription réussie !");

                Seconnecter();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de l'inscription.");
            }
        }
    } while (reponse == 'N');

    sb.close(); // Fermeture du scanner
}


   
   public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       int rep;

         System.out.println("\t ___________________________________________________________________________");
         System.out.println("\t|                                                                           |");
         System.out.println("\t|                    Bienvenue dans la bibliotheque de ESP                  |");
         System.out.println("\t|                                                                           |");
         System.out.println("\t|___________________________________________________________________________|");
   
         while (true) {
   System.out.println("Que voulez faire:\n"); 
   System.out.println("1.Se Connecter\n"); 
   System.out.println("2.S'inscrire\n");
   System.out.println("3.Pour Quitter");

   try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
    
    

     if (sc.hasNextInt()) {
                rep = sc.nextInt();

                switch (rep) {
                    case 1:
                    
                    Seconnecter();
                    break;


                    case 2:
                    
                         
                        Inscription(connection);
                        
                         break;



                    case 3:
                            System.exit(0);


                    default:
                        System.out.println("Erreur! Veuillez recommencer.");
                        break;
                }
            } 
            
                    else {
                        System.out.println("Erreur! Veuillez saisir un entier.");
                        sc.next(); // Pour vider le scanner
            }


   } catch (SQLException e) {
    e.printStackTrace();
          }
       }
   }
}
   



   
  


   

    

 

  
    


        
    

      
          
          

    
         


      
     


    
