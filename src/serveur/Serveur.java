package serveur;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

/*
 * Définition d'un serveur
 * 
 * @author Michel Sokolowski : msokolowski@nsiservices.com
 */

public class Serveur {
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private Dico dico;
	private int nbClient;
	
	/*
	 * Constructeur du Serveur
	 * 
	 * @param cheminFichierDico Chemin du fichier contenant les données
	 */
	public Serveur(String cheminFichierDico) {
		nbClient = 0;
		// chargement du dictionnaire
		dico = new Dico(cheminFichierDico);
		dico.charger();
		try {
			System.out.println("Lancement du serveur");
			
			// association d'un port au service
			serverSocket = new ServerSocket(5000);
			
			while (nbClient < 4) {
				nbClient++;
				System.out.println("\nJe suis en attente d'un client");
				
				// création d'une connexion
				clientSocket = serverSocket.accept();
				
				// LACHER LES THREADS ICI !!!!
				Thread threadServeur = new ThreadServeur(this);
				threadServeur.start();
				
				
				
			}
			System.out.println("\nJ'ai eu assez de clients");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Accesseur et modificateur
	 */
	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	/*
	 * Méthode qui traite le message envoyé par le client
	 * 
	 * @param message Message envoyé par le client
	 * @return Renvoie la réponse selon la commande
	 */
	public synchronized String traiterMessage(String message) {
		
		if (message==null) {
			return null;
		}
		
		// Décomposition du message
		StringTokenizer st = new StringTokenizer(message);
		int nbTokens = st.countTokens();
		String commande = st.nextToken();
		String clef = st.nextToken();
		
		// PUT : Insertion d'une nouvelle entrée
		if (nbTokens==3 && commande.equals("PUT")) {
			String valeur = st.nextToken();
			if (!dico.verifier(clef)) {
				try {
					// insérer
					dico.creer(clef, valeur);
					System.out.println("Insertion de " + clef + " avec le numéro : " + valeur);
					return clef + "/" + valeur + " ajouté(e) !";
				} catch (IOException e) {
					e.printStackTrace();
					return "Erreur lors de l'insertion";
				}
			} else {
				return "Impossible d'ajouter " + clef + " car il ou elle existe déjà !";
			}
		
		// GET : Obtenir la valeur d'une clef
		} else if (nbTokens==2 && commande.equals("GET")) {
			if (dico.verifier(clef)) {
				// obtenir
				return clef + " : " + dico.consulter(clef);
			} else {
				return clef + " n'existe pas :/";
			}
		
		// Erreur dans le message
		} else {
			return "Mauvaise commande envoyée ! --> 'PUT clef valeur' ou 'GET clef'";
		}

	}
}