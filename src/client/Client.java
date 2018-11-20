package client;

import java.io.*;
import java.net.*;

/*
 * Définition d'un Client
 * 
 * @author Michel Sokolowski : msokolowski@nsiservices.com
 */

public class Client {

	private Socket clientSocket;
	private String nomServeur;
	private InetAddress adresse;
	private BufferedWriter ecriture;
	private BufferedReader lecture;
	
	/*
	 * Constructeur d'un client
	 * 
	 * @param idServeur Nom du serveur
	 */
	public Client(String idServeur) {
		nomServeur = idServeur;
		try{
			//System.out.println("Le client à lui même - Je souhaite connaître le numéro de : " + nomDeLaPersonne);
			adresse = InetAddress.getByName(nomServeur);
			clientSocket = new Socket(adresse,5000);

			//ouverture du flux d'écriture du client
			ecriture = new BufferedWriter(
			 new OutputStreamWriter(clientSocket.getOutputStream()));
			
			//ouverture du flux de lecture du client
			lecture = new BufferedReader(
			 new InputStreamReader(clientSocket.getInputStream()));	
			
		} catch(UnknownHostException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Méthode pour envoyer un message et en recevoir la réponse
	 * 
	 * @param message Message à envoyer
	 */
	public void envoyerMessage(String message) {
		
		try {
			//écriture dans le flux de la question posée au serveur
			ecriture.write(message);
			ecriture.newLine();
			ecriture.flush();
			System.out.println("Réponse: " + lecture.readLine());
			
		} catch(UnknownHostException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Ferme les flux d'écriture et de lecture
	 * Ferme le client
	 */
	public void fermer() {
		try {
			//fermeture des flux
			ecriture.close();
			lecture.close();
			
			//fermeture de la connexion
			clientSocket.close();
			
		} catch(UnknownHostException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}