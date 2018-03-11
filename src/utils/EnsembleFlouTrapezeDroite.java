package utils;

// Ensemble flou particulier : 1/2 trapèze droite
// Forme :
// *         __________
// *        / 
// *       /  
// *______/
// ***********************
public class EnsembleFlouTrapezeDroite extends EnsembleFlou {
    // Constructeur
    public EnsembleFlouTrapezeDroite(double min, double max, double finPlateauBas, double debutPlateauHaut) {
        super(min, max);
        Ajouter(new Point2D(min, 0));
        Ajouter(new Point2D(finPlateauBas, 0));
        Ajouter(new Point2D(debutPlateauHaut, 1));
        Ajouter(new Point2D(max, 1));
    }
}
