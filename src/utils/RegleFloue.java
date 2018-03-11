package utils;

import java.util.ArrayList;

// Classe représentant une règle floue, avec plusieurs prémisses et une conclusion
public class RegleFloue {
    protected ArrayList<ExpressionFloue> premisses;
    protected ExpressionFloue conclusion;
    
    // Constructeur
    public RegleFloue(ArrayList<ExpressionFloue> _premisses, ExpressionFloue _conclusion) {
        premisses = _premisses;
        conclusion = _conclusion;
    }

    // Constructeur à partir d'une chaine de caractères
    public RegleFloue(String regleStr, ControleurFlou controleur) {
        // Passage de la règle en majuscule
        regleStr = regleStr.toUpperCase();
        
        // Séparation prémisses / conclusion (mot-clé THEN)
        String[] regle = regleStr.split(" THEN ");
        if (regle.length == 2) {
            // On traite les prémisses en enlevant le IF puis en coupant sur AND
            regle[0] = regle[0].replaceFirst("IF ", "").trim();
            String[] premissesStr = regle[0].split(" AND ");
            premisses = new ArrayList();
            for(String exp : premissesStr) {
                // On coupe sur le mot-clé "IS", et on créé une expression floue
                String[] parties = exp.trim().split(" IS ");
                if (parties.length == 2) {
                    ExpressionFloue expFloue = new ExpressionFloue(controleur.VariableLinguistiqueParNom(parties[0]), parties[1]);
                    premisses.add(expFloue);
                }
            }
            // On traite la conclusion
            String[] concluStr = regle[1].trim().split(" IS ");
            if (concluStr.length == 2) {
                conclusion = new ExpressionFloue(controleur.VariableLinguistiqueParNom(concluStr[0]), concluStr[1]);
            }
        }
    }
    
    // Appliquer la règle à un problème numérique donné
    // Cela produit un ensemble flou
    EnsembleFlou Appliquer(ArrayList<ValeurNumerique> probleme) {
        double degre = 1;
        // On cherche le degré de chaque prémisse
        for (ExpressionFloue premisse : premisses) {
            double degreLocal = 0;
            ValeurLinguistique vl = null;
            // On récupère la valeur pour cette premisse dans le problème à résoudre
            for (ValeurNumerique pb : probleme) {
                if (premisse.varL.equals(pb.vl)) {
                    // On a trouvé la bonne donnée du problème, on récupère son appartenance
                    vl = premisse.varL.ValeurLinguistiqueParNom(premisse.nomValeurLinguistique);
                    if (vl != null) {
                        degreLocal = vl.ValeurDAppartenance(pb.valeur);
                        break;
                    }
                }
            }
            if (vl == null) {
                // Il nous manque au moins une donnée, on ne peut pas résoudre
                return null;
            }
            // On garde le degré min
            degre = Math.min(degre, degreLocal);
        }
        return conclusion.varL.ValeurLinguistiqueParNom(conclusion.nomValeurLinguistique).ensembleFlou.MultipliePar(degre);
    }
}
