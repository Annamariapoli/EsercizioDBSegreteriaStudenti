package application;

import java.util.LinkedList;
import java.util.List;

import bean.Corso;
import bean.Studente;
import db.Dao;

public class Model {
	
	Dao dao = new Dao();
	
	public List<Corso> getTuttiCorsi(){                           //ok
		List<Corso> listaCorsi = new LinkedList<Corso>();
		listaCorsi = dao.getTuttiICorsi();
		return listaCorsi;
	}
	
	public Studente getStudente(int matricola){                    //ok
	  Studente s ;
	  s = dao.getStudente(matricola);
	  return s;
	}

	public List<Studente> iscrittiAlCorso(Corso c){                   //ok
		List<Studente> iscritti = new LinkedList<Studente>();
		iscritti = dao.cercaStudentiIscritti(c);
		return iscritti;
	}
	
	public List<Corso> corsiStudente(int matricola){                  //ok
		List<Corso> corsiStudente = new LinkedList<Corso>();
		corsiStudente = dao.corsiDelloStudente(matricola);
		return corsiStudente;
	}
	
	public boolean isStudenteIscrittoCorso(int matricola, Corso corso){        //ok
		boolean studenteIscritto = false;
		if(dao.studenteIscrittoAlCorso(matricola, corso)){
			studenteIscritto = true;
			return true;
		}
		else 
		return false;
	}
	
	public boolean iscriviStudente(int matricola, Corso corso){
		if(dao.iscrivoLoStudente(matricola, corso)){
			return true;
		}
		else return false;
	}
}
