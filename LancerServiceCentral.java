import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.ConnectException;

public class LancerServiceCentral {

	public static void main(String args[]) throws RemoteException {
		ServiceCentral serviceCentral = new ServiceCentral(); /* Crée une instance de la classe partagée */

		InterfaceServiceCentral rd = (InterfaceServiceCentral) UnicastRemoteObject.exportObject(serviceCentral, 0);
		/*
		 * cree la reference distante de l’objet. Reference distante = Signature obj +
		 * #Port
		 * Un_port = un entier particulier ou 0 pour auto-assigné
		 */

		Registry reg = LocateRegistry.getRegistry("localhost", 1099); /* Recuperation del'annuaire */

		try {
			reg.rebind("serviceCentral", rd); /*
												 * Enregistrement de la référence dans l’annuaire, associé au nom du
												 * service
												 */

			System.out.println("service central lancé");
		} catch (ConnectException e) {
			System.out.println("!!Erreur de connection avec RMI: port incorrect ou rmi non lancé");
		}
		

	}
}
