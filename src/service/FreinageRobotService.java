package service;

import java.util.List;

import utils.ControleurFlou;
import utils.VariableLinguistique;
import pojos.Resultat;

public interface FreinageRobotService {

//	public Map<Double,Double> afficherValeursVitesse(boolean isVarNumUn, ControleurFlou controleur,VariableLinguistique vitesseEntree,VariableLinguistique distance, Double valeurVar);

//	public Map<Double,Double> afficherValeurs(boolean isVarNumUn, ControleurFlou controleur,VariableLinguistique vitesseEntree,VariableLinguistique distance, Double valeurVitesse, Double valeurDistance,Double valeurPas);

	public List<Resultat> afficherValeursVitesse(boolean isVarNumUn, ControleurFlou controleur,VariableLinguistique vitesseEntree,VariableLinguistique distance, Double valeurVar);

	public List<Resultat> afficherValeurs(boolean isVarNumUn, ControleurFlou controleur,VariableLinguistique vitesseEntree,VariableLinguistique distance, Double valeurVitesse, Double valeurDistance,Double valeurPas);
}
