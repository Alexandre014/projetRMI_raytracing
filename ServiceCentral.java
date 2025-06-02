import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException; //si le serveur n’est pas actif
import java.util.ArrayList;

import raytracer.Image;
import raytracer.Scene;

public class ServiceCentral implements InterfaceServiceCentral {
    ArrayList<ServiceNoeud> noeuds;
    ArrayList<PositionImage> calculs;

    public ServiceCentral() {
        noeuds = new ArrayList<ServiceNoeud>();
        calculs = new ArrayList<PositionImage>();
    }

    public void enregistrerNoeud(ServiceNoeud noeud) throws RemoteException, ServerNotActiveException {
        noeuds.add(noeud);
        System.out.println("noeud enregistré");
    }

    public void executerRaytracing(Scene sc, int largeur, int hauteur, InterfaceServiceClient client)
            throws RemoteException, ServerNotActiveException {
        int split = 8;
        int nbLignes = largeur / split;
        int nbColonnes = hauteur / split;
        System.out.println("avant decoupage");
        // on enregistre les positions des morceaux d'images à calculer dans une liste
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                calculs.add(new PositionImage(i * split, j * split));
            }
        }
        System.out.println("decoupage effectué");
        // tant qu'il ya des calculs on parcours les noeuds et on donne un calcul à
        // realiser en appelant leur methode calculer()
        while (!calculs.isEmpty()) {
            for (ServiceNoeud noeud : noeuds) {
                try {
                    PositionImage calcul = calculs.getFirst();
                    Image morceau = noeud.calculerImage(sc, calcul.getX(), calcul.getY(), split, split);
                    client.afficherMorceau(morceau, calcul.getX(), calcul.getY(), split, split);
                    calculs.remove(calcul);
                } catch (RemoteException e) {
                    noeuds.remove(noeud);
                    System.out.println("Serveur de calcul déconnecté");
                } catch (ServerNotActiveException e) {
                    noeuds.remove(noeud);
                    System.out.println("Serveur de calcul déconnecté");
                }
            }
        }

    }
}
