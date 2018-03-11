package utils;

// Ensemble flou particulier : 1/2 trapèze gauche
// Forme :
// *_____
// *     \
// *      \
// *       \_________
// ***********************
public class EnsembleFlouTrapezeGauche extends EnsembleFlou {
    // Constructeur
    public EnsembleFlouTrapezeGauche(double min, double max, double finPlateauHaut, double debutPlateauBas) {
        super(min, max);
        Ajouter(new Point2D(min, 1));
        Ajouter(new Point2D(finPlateauHaut, 1));
        Ajouter(new Point2D(debutPlateauBas, 0));
        Ajouter(new Point2D(max, 0));
    }
}
