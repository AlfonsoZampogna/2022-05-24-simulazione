package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private ItunesDAO dao;
	private Graph grafo;
	
	
	List<Track> listaMigliore = new ArrayList<Track>();
	
	
	public Model() {
		this.dao = new ItunesDAO();
	}


	public void creaGrafo(Genre genere) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungo i vertici
		Graphs.addAllVertices(grafo, this.dao.getVertici(genere));
		
		//aggiungo gli archi
		List<Adiacenza> archi = dao.getArchi(genere);
		for(Adiacenza a : archi)
			grafo.addEdge(a.getSource(), a.getTarget(), a.getPeso());
		
		System.out.println("grafo creato!");
		System.out.println("# VERTICI: "+this.grafo.vertexSet().size());
		System.out.println("# ARCHI: "+this.grafo.edgeSet().size());
	}
	
	public List<Adiacenza> getDeltaMassimo(Genre genere){
		this.creaGrafo(genere);
		return dao.getArchiMaxDelta(genere);
	}

	public int getNumeroVertici(Genre genere){
		return dao.getVertici(genere).size();
	}
	
	public List<Track> getVertici(Genre genere){
		return dao.getVertici(genere);
	}
	
	public int getNumeroArchi(Genre genere){
		return dao.getArchi(genere).size();
	}
	
	public List<Genre> getAllGeneri(){
		return dao.getAllGenres();
	}
	
	public List<Track> calcolaLista(int memoriaMassima, Track canzonePreferita){
		//recupero la componente connessa di c
		List<Track> canzoniValide = new ArrayList<Track>();
		ConnectivityInspector<Track,DefaultWeightedEdge> ci = new ConnectivityInspector<>(this.grafo);
		canzoniValide.addAll(ci.connectedSetOf(canzonePreferita));
		List<Track> parziale = new ArrayList<Track>();
		parziale.add(canzonePreferita);
		
		cerca( memoriaMassima,  canzoniValide, parziale);
		
		return listaMigliore;
	}


	private void cerca(int memoriaMassima, List<Track> canzoniValide, List<Track> parziale) {
		//controllo soluzione migliore
		if(parziale.size() > listaMigliore.size()) {
			listaMigliore = new ArrayList<>(parziale);
		}
		
		//continuiamo
		for(Track t : canzoniValide) {
			if(!parziale.contains(t) && (sommaMemoria(parziale)+t.getBytes())<=memoriaMassima) {
				parziale.add(t);
			    cerca(memoriaMassima, canzoniValide, parziale);
			    parziale.remove(parziale.size()-1);
			}
		}
	}
	
	private int sommaMemoria(List<Track> parziale){
		int memoriaOccupata = 0;
		for(Track t : parziale) {
			memoriaOccupata+=t.getBytes();
		}
		return memoriaOccupata;
	}
	
}
