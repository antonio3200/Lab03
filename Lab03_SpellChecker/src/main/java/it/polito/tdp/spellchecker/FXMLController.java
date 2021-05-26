/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.spellchecker;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
	private Dictionary dizionario;
	private LinkedList<String> lista;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxLingua"
    private ComboBox<String> boxLingua; // Value injected by FXMLLoader

    @FXML // fx:id="txtDaCorreggere"
    private TextArea txtDaCorreggere; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorreggi"
    private Button btnCorreggi; // Value injected by FXMLLoader

    @FXML // fx:id="txtCorretto"
    private TextArea txtCorretto; // Value injected by FXMLLoader

    @FXML // fx:id="lblErrori"
    private Label lblErrori; // Value injected by FXMLLoader

    @FXML // fx:id="btnPulisci"
    private Button btnPulisci; // Value injected by FXMLLoader

    @FXML // fx:id="lblStato"
    private Label lblStato; // Value injected by FXMLLoader

    @FXML
    void doCorreggi(ActionEvent event) {

    	txtCorretto.clear();
    	lista= new LinkedList<String>();
    	if(boxLingua.getValue()==null) {
    		txtDaCorreggere.setText("Seleziona una lingua");
    		return;
    	}
    	if(!dizionario.loadDictionary(boxLingua.getValue())) {
    		txtDaCorreggere.setText("Errore nel caricamento da file");
    		return;
    	}
    	
    	String testoInput= txtDaCorreggere.getText();
    	
    	if(testoInput.isEmpty()) {
    		txtDaCorreggere.setText("Scrivere qualcosa");
    		return;
    	}
    	testoInput= testoInput.replaceAll("\n", " ");
    	testoInput = testoInput.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]]", "");
    	StringTokenizer st= new StringTokenizer(testoInput," ");
    	while(st.hasMoreTokens()) {
    		lista.add(st.nextToken());
    	}
    	List<RichWord> traduzione;
    	long start= System.nanoTime();
    	traduzione= dizionario.spellCheckText(lista);
    	long end = System.nanoTime();
    	int numErr=0;
    	StringBuilder richText = new StringBuilder();
    	for(RichWord r : traduzione) {
    		if(!r.isCorretta()) {
    			numErr++;
    			richText.append(r.getParola()+ "\n" );
    		}
    	}
    	txtCorretto.setText(richText.toString());
    	lblStato.setText("Traduzione effettuata in " + (end-start)/ 1E9 + "secondi");
    	lblErrori.setText("Il testo contiene " + numErr + "errori");
    	
    	
    	
    	
    	
    }

    @FXML
    void doPulisci(ActionEvent event) {
      txtDaCorreggere.clear();
      txtCorretto.clear();
      lblErrori.setText("Numero errori: ");
      lblStato.setText("Stato della verifica: ");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxLingua != null : "fx:id=\"boxLingua\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDaCorreggere != null : "fx:id=\"txtDaCorreggere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCorreggi != null : "fx:id=\"btnCorreggi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCorretto != null : "fx:id=\"txtCorretto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblErrori != null : "fx:id=\"lblErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPulisci != null : "fx:id=\"btnPulisci\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblStato != null : "fx:id=\"lblStato\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Dictionary model) {
		
		boxLingua.getItems().addAll("English","Italian");
		this.dizionario= model;
	}
}

