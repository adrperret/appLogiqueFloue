package utils;

// Expression floue : Variable IS Valeur
public class ExpressionFloue {
    protected VariableLinguistique varL;
    protected String nomValeurLinguistique;
    
    // Constructeur
    public ExpressionFloue(VariableLinguistique _vl, String _valeur) {
        varL = _vl;
        nomValeurLinguistique = _valeur;
    }
}
