package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pojos.Resultat;

public class LogiqueFloueRobotController {

	@FXML
	private TableView<Resultat> resultats;
	@FXML
	private TableColumn<Resultat,Double> coeffCol;
	@FXML
	private TableColumn<Resultat,Double> distanceCol;
	@FXML
	private Text actiontarget;
	@FXML
	private TextField vitesseField;
	@FXML
	private TextField distanceField;
	@FXML
	private TextField pasField;
	
	private Main main;
	
	public LogiqueFloueRobotController() {	
	}

	public void initialize() {
		distanceCol.setCellValueFactory(
        		cellData -> cellData.getValue().valeurProperty().asObject());
		coeffCol.setCellValueFactory(
        		cellData -> cellData.getValue().coeffFreinageProperty().asObject());
		
		resultats.getSelectionModel().selectedItemProperty().addListener(
				(observable,oldValue,newValue) -> handleSubmitButtonAction());	
    }

    public void setMainApp(Main main) {
        this.main = main;

        resultats.setItems(main.getData());
    }
    
    @FXML 
    protected void handleSubmitButtonAction() {
        main.setValeurDistance(Double.parseDouble(distanceField.getText()));
        main.setValeurVitesse(Double.parseDouble(vitesseField.getText()));
        main.setValeurPas(Double.parseDouble(pasField.getText()));
        
        main.initRootLayout();
        main.showResultsview();
    }

}
