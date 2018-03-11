package utils;

// Classe permettant d'associer une variable linguistique et sa valeur numérique
public class ValeurNumerique {
    protected VariableLinguistique vl;
    protected double valeur;
    
    // Constructeur
    public ValeurNumerique(VariableLinguistique _vl, double _valeur) {
        vl = _vl;
        valeur = _valeur;
    }
}
