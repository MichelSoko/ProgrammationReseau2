package client;

/*
 * Programme secondaire : Client
 * 
 * @author Michel Sokolowski : msokolowski@nsiservices.com
 */

public class ApplicationClient {

	public static void main(String args[]) {

		// Rappel : démarrer le serveur avant !
		
		Client client = new Client("localhost");
		try {
			client.envoyerMessage("PUT nana 123456789");
			client.envoyerMessage("GET nana");
			client.envoyerMessage("GET koko");
			client.fermer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}