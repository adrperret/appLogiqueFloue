package utils;

// Ensemble flou particulier : fonction triangle
// Forme :
// *         
// *        /\ 
// *       /  \
// *______/    \_________
// ***********************
public class EnsembleFlouTriangle extends EnsembleFlou {
    // Constructeur
    public EnsembleFlouTriangle(double min, double max, double debutBase, double sommet, double finBase) {
        super(min, max);
        Ajouter(new Point2D(min, 0));
        Ajouter(new Point2D(debutBase, 0));
        Ajouter(new Point2D(sommet, 1));
        Ajouter(new Point2D(finBase, 0));
        Ajouter(new Point2D(max, 0));
    }
}
