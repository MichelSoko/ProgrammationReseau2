package serveur;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;
import java.util.StringTokenizer;

/*
 * Définition d'un dictionnaire
 * 
 * @author Michel Sokolowski : msokolowski@nsiservices.com
 */

public class Dico extends Hashtable<String, String> {

	private static final long serialVersionUID = 1L;
	private URLConnection urlConnection;

	/*
	 * Constructeur du Dictionnaire
	 * 
	 * @param unURL Adresse du fichier
	 */
	public Dico(String unURL) {
		try {
			URL url = new URL(unURL);
			urlConnection = url.openConnection();
		} catch (IOException e) {
			 System.out.println(e.getMessage());
		}
	}

	/*
	 * Donne des informations sur le fichier 
	 */
	public void info() {
		System.out.println("Type de doc : " + urlConnection.getContentType());
		System.out.println("Taille du doc : " + urlConnection.getContentLength());
		System.out.println("Droit lecture : " + urlConnection.getDoInput());
		System.out.println("Droit écriture: " + urlConnection.getDoOutput());
	}
	
	/*
	 * Récupère les informations stockés dans le fichier et les stocke dans une collection
	 */
	public void charger() {
		try {
			info();
			System.out.println("***** Eléments du dictionnaire *****");

			BufferedReader flux = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

			String elementDuDico;
			while ((elementDuDico = flux.readLine()) != null) {
				StringTokenizer st;
				st = new StringTokenizer(elementDuDico, "/");
				put(st.nextToken(), st.nextToken());
			}
			flux.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Consulter le dictionnaire
	 * 
	 * @param clef Clef du hashtable Dico
	 * @return Renvoie la valeur associé à la clef
	 */
	public String consulter(String clef) {
		try {
			return get(clef);
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	/*
	 * Créer une nouvelle entrée dans le fichier
	 * 
	 * @param clef Clef de la nouvelle entrée
	 * @param valeur Valeur de la nouvelle entrée
	 */
	public void creer(String clef, String valeur) throws IOException {
		// Créer une association
		this.put(clef, valeur);
		try {
			// Ajouter l'association à la fin du fichier
			URL url = urlConnection.getURL();
			String fichier = "/var/www/html" + url.getFile();
			FileWriter fw = new FileWriter(fichier, true);
			fw.write(clef + "/" + valeur + "\n");
			fw.close();
			this.charger();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Vérifier si une clef existe dans le dictionnaire
	 * 
	 * @param clef Clef dont on souhaite vérifier l'existence
	 * @return Existence de la clef :<ul>
	 * <li>true : existe</li>
	 * <li>false : n'existe pas</li>
	 * </ul>
	 */
	public Boolean verifier(String clef) {
		return this.containsKey(clef);
	}

}