package application;
	
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pojos.Resultat;
import service.FreinageRobotService;
import utils.ControleurFlou;
import utils.EnsembleFlouTrapeze;
import utils.EnsembleFlouTrapezeDroite;
import utils.EnsembleFlouTrapezeGauche;
import utils.ValeurLinguistique;
import utils.VariableLinguistique;


public class Main extends Application {
	
	private ObservableList<Resultat> data = FXCollections.observableArrayList();
	private List<Resultat> resultatsList = new ArrayList<>();
	private FreinageRobotService fs;
	private BorderPane rootLayout;
	private Stage primaryStage;
	private ControleurFlou controleur;
	private VariableLinguistique distance, vitesseEntree, coeffFreinage;
	private Double valeurVitesse, valeurDistance, valeurPas;
	private LogiqueFloueRobotController controller;
	
	public Main() {
		fs = new service.impl.FreinageRobotServiceImpl();
        controleur = new ControleurFlou("Gestion du coefficient de vitesse du robot");
        //System.out.println("Ajout des variables d'entrée");
        // Variable linguistique d'entrée : distance (en cm, de 0 à 100)
        distance = new VariableLinguistique("Distance", 0, 100); 
        distance.AjouterValeurLinguistique(new ValeurLinguistique("Faible", new EnsembleFlouTrapezeGauche(0, 100, 10, 20))); 
        distance.AjouterValeurLinguistique(new ValeurLinguistique("Moyenne", new EnsembleFlouTrapeze(0, 100, 12.5, 20, 30, 45))); 
        distance.AjouterValeurLinguistique(new ValeurLinguistique("Grande", new EnsembleFlouTrapezeDroite(0, 100, 35, 100))); 
        controleur.AjouterVariableEntree(distance);
        
        // Variable linguistique d'entrée : vitesseEntree (en cm/s, de 0 à 40)
        vitesseEntree = new VariableLinguistique("VitesseEntree", 0, 40);
        vitesseEntree.AjouterValeurLinguistique(new ValeurLinguistique("Lente", new EnsembleFlouTrapeze(0, 40, 0, 1, 3, 4.5)));
        vitesseEntree.AjouterValeurLinguistique(new ValeurLinguistique("Moderee", new EnsembleFlouTrapeze(0, 40, 3, 5, 10, 18.5)));
        vitesseEntree.AjouterValeurLinguistique(new ValeurLinguistique("Rapide", new EnsembleFlouTrapezeDroite(0, 40, 12.5, 20)));
        controleur.AjouterVariableEntree(vitesseEntree);
        
        //System.out.println("Ajout de la variable de sortie");
        // Variable linguistique de sortie : coefficient de freinage (de 1 à 10)
        coeffFreinage = new VariableLinguistique("CoeffFreinage", 1, 10); 
        coeffFreinage.AjouterValeurLinguistique(new ValeurLinguistique("Nul", new EnsembleFlouTrapezeGauche(1, 10, 1.1, 1.25))); 
        coeffFreinage.AjouterValeurLinguistique(new ValeurLinguistique("Faible", new EnsembleFlouTrapeze(1, 10, 1.2, 2, 2.5, 3.25))); 
        coeffFreinage.AjouterValeurLinguistique(new ValeurLinguistique("Moderee", new EnsembleFlouTrapeze(1, 10, 2.75, 3, 4, 5.25)));
        coeffFreinage.AjouterValeurLinguistique(new ValeurLinguistique("Fort", new EnsembleFlouTrapeze(1, 10, 4.25, 5, 6.5, 7.25)));
        coeffFreinage.AjouterValeurLinguistique(new ValeurLinguistique("TresFort", new EnsembleFlouTrapezeDroite(1, 10, 6.75, 8)));
        controleur.AjouterVariableSortie(coeffFreinage);
        
        //System.out.println("Ajout des règles");
        // Ajout des différentes règles (7 pour couvrir les 9 cas)
        controleur.AjouterRegle("IF Distance IS Grande THEN CoeffFreinage IS Nul"); 
        controleur.AjouterRegle("IF Distance IS Moyenne AND VitesseEntree IS Lente THEN CoeffFreinage IS Nul"); 
        controleur.AjouterRegle("IF Distance IS Moyenne AND VitesseEntree IS Moderee THEN CoeffFreinage IS Nul");
        controleur.AjouterRegle("IF Distance IS Moyenne AND VitesseEntree IS Rapide THEN CoeffFreinage IS Moderee");
        controleur.AjouterRegle("IF Distance IS Faible AND VitesseEntree IS Rapide THEN CoeffFreinage IS TresFort"); 
        controleur.AjouterRegle("IF Distance IS Faible AND VitesseEntree IS Moderee THEN CoeffFreinage IS Fort");
        controleur.AjouterRegle("IF Distance IS Faible AND VitesseEntree IS Lente THEN CoeffFreinage IS Moderee");
        
        valeurVitesse = 0.0;
        valeurDistance = 0.0;
        valeurPas = 10.0;
        
        for(Resultat r : fs.afficherValeurs(true, controleur, vitesseEntree, distance, valeurVitesse, valeurDistance, valeurPas)) {
        	resultatsList.add(r);      	
        }
        data.addAll(resultatsList);
	}
	
	public ObservableList<Resultat> getData() {
		return data;
	}
	
	public Stage getPrimaryStage() {
        return primaryStage;
    }

	public Double getValeurVitesse() {
		return valeurVitesse;
	}

	public void setValeurVitesse(Double valeurVitesse) {
		this.valeurVitesse = valeurVitesse;
	}

	public Double getValeurDistance() {
		return valeurDistance;
	}

	public void setValeurDistance(Double valeurDistance) {
		this.valeurDistance = valeurDistance;
	}

	public Double getValeurPas() {
		return valeurPas;
	}

	public void setValeurPas(Double valeurPas) {
		this.valeurPas = valeurPas;
	}

	@Override
	public void start(Stage primaryStage){
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Logique floue : implémentation freinage robot");
		
		initRootLayout();
		
		showResultsview();
	}
	
	public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            for(Resultat r : fs.afficherValeurs(true, controleur, vitesseEntree, distance, valeurVitesse, valeurDistance, valeurPas)) {
            	resultatsList.add(r);
            }
            data.addAll(resultatsList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void showResultsview() {
	    try {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(Main.class.getResource("LogiqueFloueRobotDocument.fxml"));
	        SplitPane resultsview = (SplitPane) loader.load();

	        rootLayout.setCenter(resultsview);

	        controller = loader.getController();
	        controller.setMainApp(this);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void handleResults() {
		controller.handleSubmitButtonAction();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
