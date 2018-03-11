package utils;

// Classe gérant les points des fonctions d'appartenance
public class Point2D implements Comparable {

    // Coordonnées
    public double x;
    public double y;
    
    // Constructeur
    public Point2D(double _x, double _y) {
        x = _x;
        y = _y;
    }

    // Comparateur
    @Override
    public int compareTo(Object t) {
        return (int) (x - ((Point2D) t).x);
    }
    
    // Affichage
    @Override
    public String toString() {
        return "(" + x + ";" + y + ")";
    }
}
