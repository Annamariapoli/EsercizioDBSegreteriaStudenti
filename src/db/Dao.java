package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import bean.Corso;
import bean.Studente;

public class Dao {
	
	public List<Corso> getTuttiICorsi(){                       //ok
		Connection conn = DBConnect.getConnection();
		String query = "SELECT * FROM corso ";
		PreparedStatement st ;
		try{
			st = conn.prepareStatement(query);
			ResultSet res = st.executeQuery();
			List<Corso> corsi = new LinkedList<Corso>();
			while(res.next()){
				Corso c = new Corso( res.getString("codins"), res.getInt("crediti"), res.getString("nome"), res.getInt("pd"));			
				corsi.add(c);
			}
			conn.close();
			return corsi;
		} catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
		
	}
	
	public Studente getStudente(int matricola){                  //ok
		Connection conn = DBConnect.getConnection();
		String query = " SELECT * FROM studente WHERE matricola = ? ;";   //mi interessa solo cognome e nome ma metto * senno non funziona
		PreparedStatement st ;
		try{
			st= conn.prepareStatement(query);
			st.setInt(1, matricola);
			ResultSet res = st.executeQuery();
			if(res.next()){
				Studente studente = new Studente (matricola, res.getString("cognome"), res.getString("nome"), res.getString("cds"));
				return studente;
			}
		}catch (SQLException e ){
			e.printStackTrace();
		}
		return null;           //--> se non c'è nel db ?
		
	}
	
	public List<Studente> cercaStudentiIscritti (Corso corso){              //ok
		Connection conn = DBConnect.getConnection();
		String  query = "SELECT * FROM studente, iscrizione  WHERE studente.matricola = iscrizione.matricola and iscrizione.codins = ? ;";

		PreparedStatement st ;
		try{
			st = conn.prepareStatement(query);
			st.setString(1, corso.getCodins());
			List<Studente> iscritti = new LinkedList<Studente> ();
			ResultSet res = st.executeQuery();
			while(res.next()){
				Studente studente = new Studente(res.getInt("matricola"), res.getString("cognome"), res.getString("nome"), res.getString("cds"));
				iscritti.add(studente);
			}
			conn.close();
				return iscritti;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
	
		}	
	}
	
	public List<Corso> corsiDelloStudente(int matricola){              //ok
		Connection conn = DBConnect.getConnection();
		String query = "SELECT * FROM corso, iscrizione WHERE matricola = ? and iscrizione.codins=corso.codins  ; ";
	
		PreparedStatement st ;
		try{
			st = conn.prepareStatement(query);
			st.setInt(1, matricola);
			List<Corso> corsi = new LinkedList<Corso>();
			ResultSet res = st.executeQuery();
			while(res.next()){
				Corso c = new Corso(res.getString("codins"), res.getInt("crediti"), res.getString("nome"), res.getInt("pd"));
				corsi.add(c);
			}
			conn.close();
			return corsi;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean studenteIscrittoAlCorso(int matricola, Corso corso){          //ok
		Connection conn = DBConnect.getConnection();
		String query = " select  * from iscrizione where matricola = ?  and codins = ? ;";
		PreparedStatement st ;
		try{
			st = conn.prepareStatement(query);
			st.setInt(1, matricola);
			st.setString(2, corso.getCodins());
			ResultSet res = st.executeQuery(query);
			if(res.next()){
				return true;
			}
			else{ return false;}
		} catch (SQLException e ){
			e.printStackTrace();
			
		}
		return false;
	}
		
	
	//"INSERT INTO `iscritticorsi`.`iscrizione` (`matricola`, `codins`) VALUES(" + studente.getMatricola() + ",'"+ corso.getCodins() + "');"
	public boolean iscrivoLoStudente(int matricola, Corso corso){
		Connection conn =DBConnect.getConnection();
		String query = "INSERT INTO 'iscritticorsi'.'iscrizione'('matricola', 'codins') VALUES (" +matricola+ ",'"+corso.getCodins()+ "'); "; 
		PreparedStatement st ;
		try{
			st = conn.prepareStatement(query);
			st.setInt(1,  matricola);
			st.setString(1, corso.getCodins());
	        int res = st.executeUpdate();
	        if(res ==1){
	        	return true;
	        }
	        else return false;
		}catch (SQLException e ){
			e.printStackTrace();			
		}
		return false;
	}
	
}
