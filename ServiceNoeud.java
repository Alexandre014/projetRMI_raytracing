import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

import raytracer.Image;
import raytracer.Scene;

import java.io.Serializable;

public interface ServiceNoeud extends Remote, Serializable {
    public Image calculerImage(Scene sc, int x0, int y0, int l, int h) throws RemoteException, ServerNotActiveException;
}
