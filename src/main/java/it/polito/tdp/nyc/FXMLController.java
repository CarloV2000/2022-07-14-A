package it.polito.tdp.nyc;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.nyc.model.CoppiaA;
import it.polito.tdp.nyc.model.Model;
import it.polito.tdp.nyc.model.SimResult;
import it.polito.tdp.nyc.model.codiciNta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaLista"
    private Button btnCreaLista; // Value injected by FXMLLoader

    @FXML // fx:id="clPeso"
    private TableColumn<Integer, Integer> clPeso; // Value injected by FXMLLoader

    @FXML // fx:id="clV1"
    private TableColumn<?, ?> clV1; // Value injected by FXMLLoader

    @FXML // fx:id="clV2"
    private TableColumn<?, ?> clV2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBorough"
    private ComboBox<String> cmbBorough; // Value injected by FXMLLoader

    @FXML // fx:id="tblArchi"
    private TableView<?> tblArchi; // Value injected by FXMLLoader

    @FXML // fx:id="txtDurata"
    private TextField txtDurata; // Value injected by FXMLLoader

    @FXML // fx:id="txtProb"
    private TextField txtProb; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doAnalisiArchi(ActionEvent event) {
    	List<CoppiaA>res = model.getInfoArchi();
    	String s = "";
    	this.txtResult.setText("Archi con peso maggiore della media ("+model.analisiCalcoloMedia()+")\n");
    	for(CoppiaA x : res) {
    		s += x.toString();
    	}
    	this.txtResult.appendText(s);
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	String inputBorgo = this.cmbBorough.getValue();
    	
    	if(inputBorgo == null) {
    		this.txtResult.setText("Inserire un numero nel testo (n)!!!");
    		return;
    	}
    		String r = model.creaGrafo(inputBorgo);
    		this.txtResult.setText(r);
  
    }

    @FXML
    void doSimula(ActionEvent event) {
    	String durata = this.txtDurata.getText();
    	String probabilita = this.txtProb.getText();
    	Integer durataNUM;
    	Double probabilitaNUM;
    	try {
    		probabilitaNUM = Double.parseDouble(probabilita);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserire un numero come probabilita!!!");
    		return;
    	}
    	try {
    		durataNUM = Integer.parseInt(durata);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Inserire un numero come probabilita!!!");
    		return;
    	}
    	SimResult sim  = model.simula(probabilitaNUM, durataNUM);
    	String s = "";
    	for(int i = 0; i<sim.getListaCodiciNTA().size(); i++) {
    		s+= "\n"+sim.getListaCodiciNTA().get(i).getCodiceNta()+" ha ricondiviso "+sim.getListaShare().get(i)+" files.";
    		
    	}
    	this.txtResult.setText(s);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaLista != null : "fx:id=\"btnCreaLista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clPeso != null : "fx:id=\"clPeso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clV1 != null : "fx:id=\"clV1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clV2 != null : "fx:id=\"clV2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbBorough != null : "fx:id=\"cmbBorough\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tblArchi != null : "fx:id=\"tblArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDurata != null : "fx:id=\"txtDurata\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtProb != null : "fx:id=\"txtProb\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

        
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	for(String x : model.getAllBorghi())
    	     this.cmbBorough.getItems().add(x);
    }

}
