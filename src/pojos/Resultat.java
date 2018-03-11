package pojos;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Resultat {

	private DoubleProperty valeur;
	private DoubleProperty coeffFreinage;
	
	public Resultat() {
		super();
	}

	public Resultat(Double valeur, Double coefficientFreinage) {
		this();
		this.valeur = new SimpleDoubleProperty(Math.round(valeur * 100.0) / 100.0);
		this.coeffFreinage = new SimpleDoubleProperty(Math.round(coefficientFreinage * 100.0) / 100.0);
	}


	public double getValeur() {
		 return valeur.get();
	 }

	 public void setValeur(Double valeur) {
		 this.valeur.set(valeur);
	 }

	 public DoubleProperty valeurProperty() {
		 return valeur;
	 }
	 
	 public double getCoeffFreinage() {
		 return coeffFreinage.get();
	 }

	 public void setCoeffFreinage(Double coeffFreinage) {
		 this.coeffFreinage.set(coeffFreinage);
	 }

	 public DoubleProperty coeffFreinageProperty() {
		 return coeffFreinage;
	 }

}
