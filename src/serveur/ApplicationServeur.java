package serveur;

/*
 * Programme principal : Serveur
 * 
 * @author Michel Sokolowski : msokolowski@nsiservices.com
 */

public class ApplicationServeur {

	// 1) EXECUTER ApplicationServeur AVANT ApplicationClient

	// paramètres à indiquer lors de l'execution
	// args[0] = protocole + ip + chemin absolu du fichier dico.txt
	// <protocol>://<host[:port]>/<path>
	// http://localhost/dico.txt

	// important : installer apache2
	// copier-coller dico.txt dans /var/www/html (sous Linux)

	public static void main(String args[]) {

		if (args.length != 1) {
			System.exit(0);
		}
		new Serveur(args[0]);

	}

}