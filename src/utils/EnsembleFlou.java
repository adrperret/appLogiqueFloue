package utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.StringJoiner;

// Classe principale gérant les ensembles flous
public class EnsembleFlou {

    // Attributs
    protected ArrayList<Point2D> points;
    protected double min;
    protected double max;
    protected DecimalFormat f = new DecimalFormat("##.00");
    
    // Constructeur
    public EnsembleFlou(double _min, double _max) {
        points = new ArrayList();
        min = _min;
        max = _max;
    }
    
    // Ajout d'un point
    public void Ajouter(Point2D pt) {
        points.add(pt);
        Collections.sort(points);
    }
    public void Ajouter(double x, double y) {
        Point2D pt = new Point2D(x,y);
        Ajouter(pt);
    }
    
    // Affichage
    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" ");
        sj.add("[" + min + "-" + max + "]:");
        for (Point2D pt : points) {
            sj.add(pt.toString());
        }
        return sj.toString();
    }
    
    // Opérateur de comparaison (on compare les chaines résultantes)
    @Override
    public boolean equals(Object pt2) {
        return toString().equals(((Point2D)pt2).toString());
    }
    
    // Opérateur de multiplication
    public EnsembleFlou MultipliePar(double valeur) {
        EnsembleFlou ens = new EnsembleFlou(min, max);
        for(Point2D pt : points) {
            ens.Ajouter(pt.x, pt.y * valeur);
        }
        return ens;
    }
    
    // Opérateur NOT (négation)
    public EnsembleFlou Non() {
        EnsembleFlou ens = new EnsembleFlou(min, max);
        for (Point2D pt : points) {
            ens.Ajouter(pt.x, 1 - pt.y);
        }
        return ens;
    }
    
    // Calcul du degré d'appartenance d'un point
    public double ValeurDAppartenance(double valeur) {
        // Cas 1 : à l'extérieur de l'intervalle de l'ensemble flou
        if (valeur < min || valeur > max || points.size() < 2) {
            return 0;
        }
        
        Point2D ptAvant = points.get(0);
        Point2D ptApres = points.get(1);
        int index = 0;
        while(valeur >= ptApres.x) {
            index++;
            ptAvant = ptApres;
            ptApres = points.get(index);
        }
        
        if (ptAvant.x == valeur) {
            // On a un point sur cette valeur
            return ptAvant.y;
        }
        else {
            // On applique l'interpolation
            return ((ptAvant.y - ptApres.y) * (ptApres.x - valeur) / (ptApres.x - ptAvant.x) + ptApres.y);
        }
    }
    
    // Opérateur ET
    public EnsembleFlou Et(EnsembleFlou e2) {
        return Fusionner(this, e2, "Min");
    }
    
    // Opérateur OU
    public EnsembleFlou Ou(EnsembleFlou e2) {
        return Fusionner(this, e2, "Max");
    }
    
    // Méthode min ou max
    private static double Optimum(double valeur1, double valeur2, String methode) {
        if (methode.equals("Min")) {
            return Math.min(valeur1, valeur2);
        }
        else {
            return Math.max(valeur1, valeur2);
        }
    }
    
    // Méthode générique
    private static EnsembleFlou Fusionner(EnsembleFlou e1, EnsembleFlou e2, String methode) {
        // Création du résultat
        EnsembleFlou resultat = new EnsembleFlou(Math.min(e1.min, e2.min), Math.max(e1.max, e2.max));
        
        // On va parcourir les listes via les itérateurs
        Iterator<Point2D> iterateur1 = e1.points.iterator();
        Point2D ptEnsemble1 = iterateur1.next();
        Point2D ancienPtEnsemble1 = ptEnsemble1;
        Iterator<Point2D> iterateur2 = e2.points.iterator();
        Point2D ptEnsemble2 = iterateur2.next();
        
        // On calcule la position relative des deux courbes
        int anciennePositionRelative;
        int nouvellePositionRelative = (int) Math.signum(ptEnsemble1.y - ptEnsemble2.y);
                
        boolean liste1finie = false;
        boolean liste2finie = false;
        // Boucle sur tous les points des deux collections
        while(!liste1finie && !liste2finie) {
            // On récupère les abscisses des points en cours
            double x1 = ptEnsemble1.x;
            double x2 = ptEnsemble2.x;
            
            // Calcul des positions relatives
            anciennePositionRelative = nouvellePositionRelative;
            nouvellePositionRelative = (int) Math.signum(ptEnsemble1.y - ptEnsemble2.y);
            
            // Les courbes se sont-elles inversées ?
            // Sinon, a-t-on deux ou un seul point à prendre en compte ?
            if (anciennePositionRelative != nouvellePositionRelative && anciennePositionRelative != 0 && nouvellePositionRelative !=0) {
                // On doit calculer le point d'intersection
                double x = (x1 == x2 ? ancienPtEnsemble1.x : Math.min(x1,x2));
                double xPrime = Math.max(x1, x2);
                
                // Calcul des pentes
                double p1 = e1.ValeurDAppartenance(xPrime) - e1.ValeurDAppartenance(x) / (xPrime - x);
                double p2 = e2.ValeurDAppartenance(xPrime) - e2.ValeurDAppartenance(x) / (xPrime - x);
                // Calcul du delta
                double delta = 0;
                if ((p2-p1) != 0) {
                    delta = (e2.ValeurDAppartenance(x) - e1.ValeurDAppartenance(x)) / (p1 - p2);
                }
                
                // Ajout du point d'intersection au résultat
                resultat.Ajouter(x + delta, e1.ValeurDAppartenance(x + delta));
                
                // On passe au point suivant
                if (x1 < x2) {
                    ancienPtEnsemble1 = ptEnsemble1;
                    if (iterateur1.hasNext()) {
                        ptEnsemble1 = iterateur1.next();
                    }
                    else {
                        liste1finie = true;
                        ptEnsemble1 = null;
                    }
                }
                else if (x1 > x2) {
                    if (iterateur2.hasNext()) {
                        ptEnsemble2 = iterateur2.next();
                    }
                    else {
                        ptEnsemble2 = null;
                        liste2finie = true;
                    }
                }
            }
            else if (x1 == x2) {
                // Deux points à la même abscisse, il suffit de garder le bon
                resultat.Ajouter(x1, Optimum(ptEnsemble1.y, ptEnsemble2.y, methode));
                
                // On passe au point suivant
                if (iterateur1.hasNext()) {
                    ancienPtEnsemble1 = ptEnsemble1;
                    ptEnsemble1 = iterateur1.next();
                }
                else {
                    ptEnsemble1 = null;
                    liste1finie = true;
                }
                if (iterateur2.hasNext()) {
                    ptEnsemble2 = iterateur2.next();
                }
                else {
                    ptEnsemble2 = null;
                    liste2finie = true;
                }
            }
            else if (x1 < x2) {
                // La courbe 1 a un point avant
                // On calcule le degré pour la deuxième et on garde le bon
                resultat.Ajouter(x1, Optimum(ptEnsemble1.y, e2.ValeurDAppartenance(x1), methode));
                // On se décale
                if (iterateur1.hasNext()) {
                    ancienPtEnsemble1 = ptEnsemble1;
                    ptEnsemble1 = iterateur1.next();
                }
                else {
                    ptEnsemble1 = null;
                    liste1finie = true;
                }
            }
            else {
                // Dernier cas, c'est la courbe 2 qui a un point avant
                // On calcule le degré pour la première et on garde le bon
                resultat.Ajouter(x2, Optimum(e1.ValeurDAppartenance(x2), ptEnsemble2.y, methode));
                // On se décale
                if (iterateur2.hasNext()) {
                    ptEnsemble2 = iterateur2.next();
                }
                else {
                    ptEnsemble2 = null;
                    liste2finie = true;
                }
            }
        }
        
        // Ici, au moins une des listes est finie
        // On ajoute les points restants
        if (!liste1finie) {
            while(iterateur1.hasNext()) {
                ptEnsemble1 = iterateur1.next();
                resultat.Ajouter(ptEnsemble1.x, Optimum(ptEnsemble1.y, 0, methode));
            }
        }
        else if (!liste2finie) {
            while(iterateur2.hasNext()) {
                ptEnsemble2 = iterateur2.next();
                resultat.Ajouter(ptEnsemble2.x, Optimum(ptEnsemble2.y, 0, methode));
            }
        }
        
        return resultat;
    }
    
    public double Barycentre() {
        // Si moins de deux points, pas de barycentre
        if (points.size() <= 2) {
            return 0;
        }
        else {
            // Initialisation des aires
            double airePonderee = 0;
            double aireTotale = 0;
            double aireLocale;
            // Parcours de la liste en conservant 2 points
            Point2D ancienPt = null;
            for(Point2D pt : points) {
                if (ancienPt != null) {
                    // Calcul du barycentre local
                    if (ancienPt.y == pt.y) {
                        // C'est un rectangle, barycentre au centre
                        aireLocale = pt.y * (pt.x - ancienPt.x);
                        aireTotale += aireLocale;
                        airePonderee += aireLocale * ((pt.x - ancienPt.x) / 2.0 + ancienPt.x);
                    }
                    else {
                        // C'est un trapèze, qu'on peut décomposer
                        // comme un rectangle avec un triangle rectangle dessus
                        // On sépare les deux formes
                        // Temps 1 : rectangle
                        aireLocale = Math.min(pt.y, ancienPt.y) * (pt.x - ancienPt.x);
                        aireTotale += aireLocale;
                        airePonderee += aireLocale * ((pt.x - ancienPt.x) / 2.0 + ancienPt.x);
                        // Temps 2 : triangle rectangle
                        aireLocale = (pt.x - ancienPt.x) * Math.abs(pt.y - ancienPt.y) / 2.0;
                        aireTotale += aireLocale;
                        if (pt.y > ancienPt.y) {
                            // Barycentre à 1/3 coté pt
                            airePonderee += aireLocale * (2.0/3.0 * (pt.x - ancienPt.x) + ancienPt.x);
                        }
                        else {
                            // Barycentre à 1/3 coté ancienPt
                            airePonderee += aireLocale * (1.0/3.0 * (pt.x - ancienPt.x) + ancienPt.x);
                        }
                    }
                }
                ancienPt = pt;
            }
            // On renvoie la coordonnée du barycentre
            return airePonderee / aireTotale;
        }
    }
}
