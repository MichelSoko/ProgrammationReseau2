package serveur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/*
 * Définition d'un Thread Serveur
 * 
 * @author Michel Sokolowski : msokolowski@nsiservices.com
 */

public class ThreadServeur extends Thread {
	
	private Socket clientSocket;
	private Serveur serveur;
	private BufferedWriter ecriture;
	private BufferedReader lecture;
	
	/*
	 * Accesseurs et modificateurs
	 */
	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	public Serveur getServeur() {
		return serveur;
	}

	public void setServeur(Serveur serveur) {
		this.serveur = serveur;
	}

	/*
	 * Constructeur d'un ThreadServeur
	 * 
	 * @param serveur Serveur contenant le dictionnaire
	 */
	public ThreadServeur(Serveur serveur) throws IOException {
		
		this.setClientSocket(serveur.getClientSocket());
		this.setServeur(serveur);
		
		// ouverture du flux d'écriture du serveur
		this.ecriture = new BufferedWriter(
		 new OutputStreamWriter(clientSocket.getOutputStream()));
				
		// ouverture du flux de lecture du serveur
		this.lecture = new BufferedReader(
		 new InputStreamReader(clientSocket.getInputStream()));
		
	}
	
	
	/*
	 * Méthode run du Thread
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		
		while (true) {
			try {			
				// lecture du message
				String reponse = serveur.traiterMessage(lecture.readLine());
				
				// recherche du numéro de téléphone dans le dictionnaire
				if (reponse!=null) {
					ecriture.write(reponse);
					ecriture.newLine();
					ecriture.flush();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
