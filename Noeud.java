import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException; //si le serveur n’est pas actif
import java.rmi.server.RemoteServer;

import raytracer.Image;
import raytracer.Scene;

public class Noeud implements ServiceNoeud {

	public Image calculerImage(Scene sc, int x0, int y0, int l, int h)
			throws RemoteException, ServerNotActiveException {
		// String host = "";
		// try {
		// host = RemoteServer.getClientHost();
		// System.out.println("Adresse IP du client :" + host);
		// } catch (ServerNotActiveException e) {
		// }
		System.out.println("calcul du pixel " + x0 + ";" + y0);
		return sc.compute(x0, y0, l, h);
	}
}
