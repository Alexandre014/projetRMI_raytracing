import raytracer.Disp;
import raytracer.Scene;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException; //erreur de reseau
import java.rmi.NotBoundException; //le nom du service dans l’annuaire est incorrect
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.ConnectException;

public class LancerClient {
	public static void main(String[] args) throws RemoteException, NotBoundException, ServerNotActiveException {
		// demande l’adresse IP du service
		String adrAnnuaire = "localhost";
		try {
			if (args[0] != null) {
				adrAnnuaire = args[0];
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("pas d'adresse donnée, utilisation de localhost");
		}
		// Le fichier de description de la scène si pas fournie
		String fichier_description = "simple.txt";

		// largeur et hauteur par défaut de l'image à reconstruire
		int largeur = 512, hauteur = 512;

		try {
			/* se connecter à l'annuaire de la machine */
			Registry reg = LocateRegistry.getRegistry(adrAnnuaire); /* se connecter à l'annuaire du serveur */

			String nomService = reg.list()[0];
			System.out.println(nomService);
			InterfaceServiceCentral serviceCentral = (InterfaceServiceCentral) reg.lookup(nomService); // nom du

			// service
			// associé
			// à
			// la reference dans
			// l’annuaire
			// fausse instance de classe, récupère la référence distante, qui permet
			// d’appeler des méthodes exécutées sur le serveur
			// throw NotBoundException, RemoteException

			// debut du programme
			// création d'une fenêtre
			Disp disp = new Disp("Raytracer", largeur, hauteur);

			// Initialisation d'une scène depuis le modèle
			Scene scene = new Scene(fichier_description, largeur, hauteur);
			Client client = new Client(disp, scene);

			InterfaceServiceClient rd = (InterfaceServiceClient) UnicastRemoteObject.exportObject(client, 0);
			// faire une interfcace remote de svene

			System.out.println("demande l'image");
			serviceCentral.executerRaytracing(scene, largeur, hauteur, rd);

		} catch (ConnectException e) {
			System.out.println("!! Erreur de connexion au service");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out.println("!! Service introuvable dans l'annuaire");
		} catch (RemoteException e) {
			System.out.println("!! Erreur de communication RMI");
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new Error("Pas de service dans l'agenda");
		}
	}
}
