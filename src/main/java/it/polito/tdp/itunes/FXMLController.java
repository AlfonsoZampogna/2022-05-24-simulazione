/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.itunes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.itunes.model.Adiacenza;
import it.polito.tdp.itunes.model.Genre;
import it.polito.tdp.itunes.model.Model;
import it.polito.tdp.itunes.model.Track;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
	private boolean grafoCreato = false;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaLista"
    private Button btnCreaLista; // Value injected by FXMLLoader

    @FXML // fx:id="btnMassimo"
    private Button btnMassimo; // Value injected by FXMLLoader

    @FXML // fx:id="cmbCanzone"
    private ComboBox<Track> cmbCanzone; // Value injected by FXMLLoader

    @FXML // fx:id="cmbGenere"
    private ComboBox<Genre> cmbGenere; // Value injected by FXMLLoader

    @FXML // fx:id="txtMemoria"
    private TextField txtMemoria; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void btnCreaLista(ActionEvent event) {
    	txtResult.clear();
        if(cmbCanzone.getValue()==null) {
        	txtResult.appendText("inserisci una canzone!");
        	return;
        }
    	Track canzonePreferita = cmbCanzone.getValue();
    	int m;
    	try {
    		m = Integer.parseInt(txtMemoria.getText());
    	}catch(NumberFormatException e) {
    		txtResult.appendText("inserisci una valore intero di memoria!");
        	return;
    	}
    	if(!grafoCreato) {
    		txtResult.appendText("prima crea il grafo!");
    		return;
    	}
    	
    	txtResult.appendText("LISTA CANZONI MIGLIORI: "+"\n");
    	for(Track t : this.model.calcolaLista(m, canzonePreferita)) {
    		txtResult.appendText(t+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	txtResult.clear();
    	if(cmbGenere.getValue()==null) {
    		txtResult.appendText("inserisci un genere!");
    		return;
    	}
    	Genre genere = cmbGenere.getValue();
    	this.model.creaGrafo(genere);
    	grafoCreato=true;
    	txtResult.appendText("GRAFO CREATO!\n");
    	txtResult.appendText("#VERTICI = "+this.model.getNumeroVertici(genere));
    	txtResult.appendText("\n#ARCHI = "+this.model.getNumeroArchi(genere));
    	
    	this.cmbCanzone.getItems().addAll(this.model.getVertici(cmbGenere.getValue()));
    }

    @FXML
    void doDeltaMassimo(ActionEvent event) {
    	txtResult.clear();
    	if(cmbGenere.getValue()==null) {
    		txtResult.appendText("inserisci un genere!");
    		return;
    	}
    	if(!grafoCreato) {
    		txtResult.appendText("prima crea il grafo!");
    		return;
    	}
    	txtResult.appendText("COPPIA CANZONI DELTA MASSIMO: \n");
    	Genre genere = cmbGenere.getValue();
    	List<Adiacenza> result = this.model.getDeltaMassimo(genere);
    	txtResult.appendText(result.toString());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaLista != null : "fx:id=\"btnCreaLista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnMassimo != null : "fx:id=\"btnMassimo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbCanzone != null : "fx:id=\"cmbCanzone\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbGenere != null : "fx:id=\"cmbGenere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMemoria != null : "fx:id=\"txtMemoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	this.cmbGenere.getItems().addAll(this.model.getAllGeneri());
    	
    	txtMemoria.setText(null);
    }

}
