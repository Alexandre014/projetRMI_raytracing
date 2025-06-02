import java.io.Serializable;
import java.rmi.Remote;
import raytracer.Image;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface InterfaceServiceClient extends Remote, Serializable {
    public void afficherMorceau(Image image, int x0, int y0, int largeur, int hauteur)
            throws RemoteException, ServerNotActiveException;
}