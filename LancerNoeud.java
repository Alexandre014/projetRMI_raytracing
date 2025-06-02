import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException; //erreur de reseau
import java.rmi.NotBoundException; //le nom du service dans l’annuaire est incorrect
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.ConnectException;

public class LancerNoeud {
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

		try {
			/* se connecter à l'annuaire de la machine */
			Registry reg = LocateRegistry.getRegistry(adrAnnuaire); /* se connecter à l'annuaire du serveur */

			InterfaceServiceCentral serviceCentral = (InterfaceServiceCentral) reg.lookup("serviceCentral"); // nom du
																												// service
																												// associé
																												// à
			// la reference dans
			// l’annuaire
			// fausse instance de classe, récupère la référence distante, qui permet
			// d’appeler des méthodes exécutées sur le serveur
			// throw  // NotBoundException, RemoteException

			Noeud noeud = new Noeud(); /*
										 * Crée une instance de
										 * Compteur
										 */
			ServiceNoeud rd = (ServiceNoeud) UnicastRemoteObject.exportObject(noeud, 0);
			/*
			 * cree la reference distante de l’objet. Reference distante = Signature obj +
			 * #Port
			 * Un_port = un entier particulier ou 0 pour auto-assigné
			 */

			serviceCentral.enregistrerNoeud(rd);
			System.out.println("noeud enregistré");

		} catch (ConnectException e) {
			System.out.println("!! Erreur de connexion au service");
		} catch (NotBoundException e) {
			System.out.println("!! Service introuvable dans l'annuaire");
		} catch (RemoteException e) {
			System.out.println("!! Erreur de communication RMI");
		}

	}
}
