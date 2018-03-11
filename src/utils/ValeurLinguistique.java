package utils;

// Classe représentant une valeur linguistique comportant un nom et un ensemble flou
public class ValeurLinguistique {
    // Ensemble flou lié
    protected EnsembleFlou ensembleFlou;

    // Nom de la valeur
    protected String nom;

    // Constructeur
    public ValeurLinguistique(String _nom, EnsembleFlou _ef) {
        ensembleFlou = _ef;
        nom = _nom;
    }
    
    double ValeurDAppartenance(double valeur) {
        return ensembleFlou.ValeurDAppartenance(valeur);
    }
}
