package utils;

// Ensemble flou particulier : fonction trapèze
// Forme :
// *         ____
// *        /    \ 
// *       /      \
// *______/        \______
// ***********************
public class EnsembleFlouTrapeze extends EnsembleFlou {
    // Constructeur
    public EnsembleFlouTrapeze(double min, double max, double debutBase, double debutPlateau, double finPlateau, double finBase) {
        super(min, max);
        Ajouter(new Point2D(min, 0));
        Ajouter(new Point2D(debutBase, 0));
        Ajouter(new Point2D(debutPlateau, 1));
        Ajouter(new Point2D(finPlateau, 1));
        Ajouter(new Point2D(finBase, 0));
        Ajouter(new Point2D(max, 0));
    }
}
