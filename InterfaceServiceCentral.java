import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

import raytracer.Scene;

public interface InterfaceServiceCentral extends Remote {
    public void enregistrerNoeud(ServiceNoeud noeud) throws RemoteException, ServerNotActiveException;

    public void executerRaytracing(Scene sc, int largeur, int hauteur, InterfaceServiceClient client)throws RemoteException, ServerNotActiveException;
}
