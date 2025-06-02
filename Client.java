import raytracer.Disp;
import raytracer.Image;
import raytracer.Scene;

import java.time.Duration;
import java.time.Instant;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class Client implements InterfaceServiceClient {
        Disp disp;
        Scene scene;

        public Client(Disp disp, Scene scene) {
                this.disp = disp;
                this.scene = scene;
        }

        @Override
        public void afficherMorceau(Image image, int x0, int y0, int largeur, int hauteur)
                        throws RemoteException, ServerNotActiveException {

                // Calcul de l'image de la scène les paramètres :
                // - x0 et y0 : correspondant au coin haut à gauche
                // - l et h : hauteur et largeur de l'image calculée
                // Ici on calcule toute l'image (0,0) -> (largeur, hauteur)

                // Chronométrage du temps de calcul
                Instant debut = Instant
                                .now();
                System.out.println("Calcul de l'image :\n - Coordonnées : " + x0 + "," + y0 + "\n - Taille " + largeur
                                + "x" + hauteur);
                Image image1 = scene.compute(x0, y0, largeur, hauteur);
                Image image2 = scene.compute(largeur / 2, hauteur / 2, largeur, hauteur);
                Instant fin = Instant.now();

                long duree = Duration.between(debut, fin).toMillis();

                System.out.println("Image calculée en :" + duree + " ms");

                // Affichage de l'image calculée
                disp.setImage(image1, x0, y0);
                disp.setImage(image2, largeur / 2, hauteur / 2);

        }
}