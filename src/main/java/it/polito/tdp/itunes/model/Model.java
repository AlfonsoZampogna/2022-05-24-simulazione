package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private ItunesDAO dao;
	private Graph<Track,DefaultWeightedEdge> grafo;
	private List<Genre> generi;
	
	private List<Track> bestList;
	
	private double deltaMax;
	
	public Model() {
		this.dao = new ItunesDAO();
		this.generi = new ArrayList<Genre>();
	}
	
	public List<Genre> getAllGenres(){
		if(this.generi.isEmpty())
			generi = this.dao.getAllGenres();
		return generi;
	}
	
	public void creaGrafo(Genre genere) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Map<Integer,Track> vertici = new HashMap<Integer,Track>();
		for(Track t : this.dao.getGenreTracks(genere))
			vertici.put(t.getTrackId(), t);
		Graphs.addAllVertices(this.grafo, vertici.values());
		
		for(Adiacenza a : this.dao.getConnectedTracks(genere)) {
			Track t1 = vertici.get(a.getIdTrack1());
			Track t2 = vertici.get(a.getIdTrack2());
			Graphs.addEdgeWithVertices(this.grafo, t1, t2, a.getPeso());
		}	
	}
	
	public Collection<Track> getTrackVertici(Genre genere){
		Map<Integer,Track> vertici = new HashMap<Integer,Track>();
		for(Track t : this.dao.getGenreTracks(genere))
			vertici.put(t.getTrackId(), t);
		return vertici.values();
	}
	
	public int getNumVertici(){
		return this.grafo.vertexSet().size();
	}
	
	public int getNumArchi() {
		return this.grafo.edgeSet().size();
	}
	
	
	
	public List<Adiacenza> getDeltaMassimo(){
		this.deltaMax = Double.MIN_VALUE;
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		for(DefaultWeightedEdge e : this.grafo.edgeSet())
			if(this.grafo.getEdgeWeight(e)>this.deltaMax)
				this.deltaMax=this.grafo.getEdgeWeight(e);
		
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e)==this.deltaMax) {
				Track t1 = this.grafo.getEdgeSource(e);
				Track t2 = this.grafo.getEdgeTarget(e);
				result.add(new Adiacenza(t1.getTrackId(),t1.getName(),t2.getTrackId(),t2.getName(),deltaMax));
			}
		}
		return result;
	}

	public List<Track> cercaLista(Track c, int m){
		//recupero la componenete connessa di c
		Set<Track> componenteConnessa;
		ConnectivityInspector<Track, DefaultWeightedEdge> ci = 
				new ConnectivityInspector<>(this.grafo);
		componenteConnessa = ci.connectedSetOf(c);
		
		List<Track> canzoniValide = new ArrayList<Track>();
		canzoniValide.add(c);
		componenteConnessa.remove(c);
		canzoniValide.addAll(componenteConnessa);
		
		List<Track> parziale = new ArrayList<>();
		bestList = new ArrayList<>();
		parziale.add(c);
		
		cerca(parziale,canzoniValide,m, 1);
		
		return bestList;
	}
	
	private int sommaMemoria (List<Track> canzoni) {
		int somma = 0;
		for(Track t : canzoni) {
			somma += t.getBytes();
		}
		return somma;
	}
	
	private void cerca(List<Track> parziale, List<Track> canzoniValide, int m, int L) {
		
		if(sommaMemoria(parziale) > m)
			return;
		
		//parziale è valida
		if(parziale.size() > bestList.size()) {
			bestList = new ArrayList<>(parziale);
		}
		
		if(L == canzoniValide.size())
			return;
		
		parziale.add(canzoniValide.get(L));
		cerca(parziale, canzoniValide,m, L +1);
		parziale.remove(canzoniValide.get(L));
		cerca(parziale,canzoniValide,m, L+1);
	}
}
