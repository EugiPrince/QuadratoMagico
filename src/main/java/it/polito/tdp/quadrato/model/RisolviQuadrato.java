package it.polito.tdp.quadrato.model;

import java.util.ArrayList;
import java.util.List;

public class RisolviQuadrato {
	private int N ; // lato del quadrato
	private int N2 ; // numero di caselle (N^2)
	private int magica ; // costante magica
	
	private List<List<Integer>> soluzioni;
	
	public RisolviQuadrato(int N) {
		this.N = N;
		this.N2 = N*N;
		this.magica = N*(N2+1)/2;
	}
	
	// Calcola tutti i quadrati magici
	public List<List<Integer>> quadrati() {
		List<Integer> parziale = new ArrayList<>();
		int livello = 0;
		
		this.soluzioni = new ArrayList<List<Integer>>();
		
		cerca(parziale, livello);
		
		return this.soluzioni;
	}
	
	// procedura ricorsiva (privata)
	private void cerca(List<Integer> parziale, int livello) {
		
		if(livello==N2) { //Sono nel caso terminale, il livello e' arrivato all'ultima casella
			if(controlla(parziale)) {
				// this.soluzioni.add(parziale); se faccio cosi' non va bene perche' e' il riferimento, e alla fine sara' vuota
				this.soluzioni.add(new ArrayList<>(parziale));
			}
			return;
		}
		
		// controlli intermedi quando il livello e' multiplo di N, cioe' ho delle righe complete... ovvero
		// controlla ogni riga se ha gia' senso... perche' i valori magici so gia' quali sono, quindi dopo una
		// riga posso gia' controllare se ha senso andare avanti oppure no
		if(livello%N==0 && livello!=0) {
			if(!controllaRiga(parziale, livello/N-1)) {
				return; //POTATURA dell'albero ricerca
			}
		}
		
		//Caso intermedio
		for(int valore=1; valore<=N2; valore++) {
			if(!parziale.contains(valore)) { //Non posso avere numeri ripetuti, quindi cerco se c'e' gia'
				parziale.add(valore);
				
				//RICORSIONE
				cerca(parziale, livello+1);
				
				//backtracking
				parziale.remove(parziale.size()-1);
			}
		}
	}
	
		
	/**
	 * Verifica se una soluzione rispetta tutte le somme
	 * @param parziale
	 * @return
	 */
	private boolean controlla(List<Integer> parziale) {
		if(parziale.size()!=this.N*this.N)
			throw new IllegalArgumentException("Numero di elementi insufficiente") ;
		
		// Fai la somma delle righe
		for(int riga=0; riga<this.N; riga++) {
			int somma = 0 ;
			for(int col=0; col<this.N; col++) {
				somma += parziale.get(riga*this.N+col) ;
			}
			if(somma!=this.magica)
				return false ;
		}
		
		// Fai la somma delle colonne
		for(int col=0; col<this.N; col++) {
			int somma = 0 ;
			for(int riga=0; riga<this.N; riga++) {
				somma += parziale.get(riga*this.N+col) ;
			}
			if(somma!=this.magica)
				return false ;
		}
		
		// diagonale principale
		int somma = 0;
		for(int riga=0; riga<this.N; riga++) {
			somma += parziale.get(riga*this.N+riga) ;
		}
		if(somma!=this.magica)
			return false ;
		
		// diagonale inversa
		somma = 0;
		for(int riga=0; riga<this.N; riga++) {
			somma += parziale.get(riga*this.N+(this.N-1-riga)) ;
		}
		if(somma!=this.magica)
			return false ;

		return true ;
	}
	
	private boolean controllaRiga(List<Integer> parziale, int riga) {
		int somma=0;
		for(int col=0; col<N; col++)
			somma+=parziale.get(riga*N+col);
		return somma==magica;
	}
}
