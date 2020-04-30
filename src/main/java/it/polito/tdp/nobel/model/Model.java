package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	private EsameDAO dao;
    private Set<Esame> soluzione;
    private List<Esame> esami;

    private double bestMedia;
    
    public Model() {
    	this.dao= new EsameDAO();
    	this.soluzione = new HashSet<Esame>();
    	this.esami = dao.getTuttiEsami();
    	}
    
	
	public Set<Esame> getSoluzione() {
		return soluzione;
	}


	public void setSoluzione(Set<Esame> soluzione) {
		this.soluzione = soluzione;
	}


	public double getBestMedia() {
		return bestMedia;
	}


	public void setBestMedia(double bestMedia) {
		this.bestMedia = bestMedia;
	}


	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		/**
		 * pulizia dati in memoria;
		 */
		
    	bestMedia=0;
    	this.soluzione.clear();

    	Set<Esame> parziale = new HashSet<>();
    	this.trovaCombinazione(0, parziale , numeroCrediti);
		
		return this.soluzione;
	}
	
	public void trovaCombinazione(int livello, Set<Esame> parziale, int crediti) {
		if(this.totaleCrediti(parziale)> crediti ) {
			return;
		}
		
		if(this.totaleCrediti(parziale)==crediti) {
			if(this.calcolaMedia(parziale)>this.bestMedia) {
				this.bestMedia=this.calcolaMedia(parziale);
				this.soluzione= new HashSet<>(parziale);
			}
		}
		
		
		if(livello==this.esami.size()) {	
			return;
			} 
		
		parziale.add(this.esami.get(livello));
		trovaCombinazione(livello+1, parziale, crediti);
		parziale.remove(this.esami.get(livello));
		
		trovaCombinazione(livello+1,parziale,crediti);
		
		
		
	}
	
	private double calcolaMedia(Set<Esame> parziale) {
		double somma = 0;
		double crediti = 0;
		for(Esame e : parziale) {
			somma+= (e.getVoto()*e.getCrediti());
			crediti+=e.getCrediti();
		}
		
		return somma/crediti;
	}
	
	private int totaleCrediti(Set<Esame> parziale) {
		int tot=0;
		for(Esame e: parziale) {
			tot+=e.getCrediti();
		}
		return tot;
	}

}
