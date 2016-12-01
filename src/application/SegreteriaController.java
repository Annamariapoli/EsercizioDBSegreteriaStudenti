package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import bean.Corso;
import bean.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaController {
	
	private Model model = new Model();
	
	public void setModel(Model model){
		this.model=model;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> comboCorso;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button btnCompleta;

    @FXML
    private TextField txtCognome;

    @FXML
    private TextField txnNome;

    @FXML
    private Button btnCerca;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;

    @FXML
    void doCerca(ActionEvent event) {     //ok
    	
    	if(comboCorso.getValue()==null && txtMatricola.getText().compareTo("")!=0){                                          //ok
    		int matricola = Integer.parseInt(txtMatricola.getText());
    		Studente s =null;
    		s = model.getStudente(matricola);
    		if(s == null){
    			txtResult.appendText("Lo studente non esiste nel DB! \n ");
    			return;
    		}
    		else {
    			  List<Corso> corsiDelloStudente = model.corsiStudente(matricola);
    			  txtResult.appendText("i corsi dello studente sono: \n " +corsiDelloStudente.toString());
    		}
    	}
    	
    	if(comboCorso.getValue()!= null && txtMatricola.getText().compareTo("")==0){
    	Corso corso = comboCorso.getValue();                                       //---> ok
    	List<Studente> iscrittiCorso = model.iscrittiAlCorso(corso);
    	if(iscrittiCorso.isEmpty()){
    		txtResult.appendText("Il corso non ha iscritti \n ");
    		return ;
    	}
    	else if(!iscrittiCorso.isEmpty()){
    		txtResult.appendText("Gli iscritti sono : " + iscrittiCorso );
    	}
     }	
    	
    	if(comboCorso.getValue()!= null && txtMatricola.getText().compareTo("")!=0){
    		int matricola = Integer.parseInt(txtMatricola.getText());
    	  Corso corso  = comboCorso.getValue();
    		Studente s =null;
    		s = model.getStudente(matricola);
    		if(s == null){
    			txtResult.appendText("Lo studente non esiste nel DB! \n ");
    			return;
    		}		
    		//se è iscritto o no al corso:
    		boolean studenteIscrittoCorso = false;
    		if(model.isStudenteIscrittoCorso(matricola, corso)){
    			studenteIscrittoCorso = true;
    			txtResult.appendText("Lo studente è iscritto a questo corso \n ");
    			return;
    		} else txtResult.appendText("Lo studente NON è iscritto al corso \n ");
    		       return;
    	}
    
    
    }
   

    @FXML
    void doCompleta(ActionEvent event) {                                    //ok , manca solo un controllo
    	txtResult.clear();
    	int matricola = Integer.parseInt(txtMatricola.getText());
    	//String matricola=txtMatricola.getText();
    	
    	if(matricola==0){
    		txtResult.appendText("Inserisci una matricola \n ");
    		return;
    	}
    	Studente s = model.getStudente(matricola);
    	if(model.getStudente(matricola)!= null){
    		txtCognome.setText(s.getCognome());
    		txnNome.setText(s.getNome());
    	}
    	else if(model.getStudente(matricola)==null){
    		txtResult.appendText("Lo studente non esiste ! \n ");
    		return;
    	}    	
  }

    @FXML
    void doIscrivi(ActionEvent event) {
    	if(comboCorso.getValue()==null){
    		txtResult.appendText("Seleziona un corso !  \n ");
    				return;
    	}
    	if(txtMatricola.getText().compareTo("")==0){
    		txtResult.appendText("Inserisci una matricola ! \n ");
    		return;
    	}
    	int matricola =Integer.parseInt(txtMatricola.getText());
    	Studente s = model.getStudente(matricola);
    	if(s ==null){
    		txtResult.appendText("Lo studente non esiste ! \n ");
    		return;
    	}
    	
    	Corso corso = comboCorso.getValue();
    	model.iscriviStudente(matricola, corso);
    	if(model.iscriviStudente(matricola, corso)){
    		txtResult.appendText("Studente iscritto correttamente! \n ");
    		return;
    	}
    	else txtResult.appendText("Studente gia iscritto al corso  ! \n" );

    }

    @FXML
    void doReset(ActionEvent event) {	
    	comboCorso.getItems().clear();
    	txtMatricola.clear();
    	txtCognome.clear();
    	txnNome.clear();
    	txtResult.clear();
    	

    }

    @FXML
    void initialize() {
        assert comboCorso != null : "fx:id=\"comboCorso\" was not injected: check your FXML file 'Segreteria.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Segreteria.fxml'.";
        assert btnCompleta != null : "fx:id=\"btnCompleta\" was not injected: check your FXML file 'Segreteria.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Segreteria.fxml'.";
        assert txnNome != null : "fx:id=\"txnNome\" was not injected: check your FXML file 'Segreteria.fxml'.";
        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'Segreteria.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Segreteria.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Segreteria.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Segreteria.fxml'.";

        comboCorso.getItems().addAll(model.getTuttiCorsi());    //basta questa x aggiungerli
        
        //vorrei anke uno spazio vuoto
    }
}

