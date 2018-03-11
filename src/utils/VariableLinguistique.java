package utils;

import java.util.ArrayList;

// Classe représentant une variable linguistique
public class VariableLinguistique {
    protected String nom;
    protected ArrayList<ValeurLinguistique> valeurs;
    protected double valeurMin;
    protected double valeurMax;
    
    // Constructeur
    public VariableLinguistique(String _nom, double _min, double _max) {
        nom = _nom;
        valeurMin = _min;
        valeurMax = _max;
        valeurs = new ArrayList();
    }
    
    public void AjouterValeurLinguistique(ValeurLinguistique vl) {
        valeurs.add(vl);
    }
    
    public void AjouterValeurLinguistique(String nom, EnsembleFlou ensemble) {
        valeurs.add(new ValeurLinguistique(nom, ensemble));
    }
    
    public void EffacerValeurs() {
        valeurs.clear();
    }
    
    ValeurLinguistique ValeurLinguistiqueParNom(String nom) {
        for(ValeurLinguistique vl : valeurs) {
            if (vl.nom.equalsIgnoreCase(nom)) {
                return vl;
            }
        }
        return null;
    }
}
