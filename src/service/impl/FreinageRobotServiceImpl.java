package service.impl;

import java.util.ArrayList;
import java.util.List;

import service.FreinageRobotService;
import utils.ControleurFlou;
import utils.VariableLinguistique;
import pojos.Resultat;

public class FreinageRobotServiceImpl implements FreinageRobotService{
	
	@Override
	public List<Resultat> afficherValeursVitesse(boolean isVarNumUn, ControleurFlou controleur,
			VariableLinguistique vitesseEntree, VariableLinguistique distance, Double valeurVar) {
		List<Resultat> resultats = new ArrayList<>();
		Resultat resultat = null;
        for(double i = 50 ; i > 0 ; i = i - 0.5){
            controleur.EffacerValeursNumeriques();
            controleur.AjouterValeurNumerique(vitesseEntree, valeurVar); 
            controleur.AjouterValeurNumerique(distance, i);
            resultat = new Resultat(i,controleur.Resoudre());
            resultats.add(resultat);
        }
		return resultats;
	}

	@Override
	public List<Resultat> afficherValeurs(boolean isVarNumUn, ControleurFlou controleur,
			VariableLinguistique vitesseEntree, VariableLinguistique distance, Double valeurVitesse,
			Double valeurDistance, Double valeurPas) {
		List<Resultat> resultats = new ArrayList<>();
		Resultat resultat = null;
        for(Double i = valeurDistance ; i > 0 ; i = i - (valeurPas)){
            controleur.EffacerValeursNumeriques();
            controleur.AjouterValeurNumerique(vitesseEntree, valeurVitesse); 
            controleur.AjouterValeurNumerique(distance, i);
            resultat = new Resultat(i,controleur.Resoudre());
            resultats.add(resultat);
        } 
		return resultats;
	}
}

