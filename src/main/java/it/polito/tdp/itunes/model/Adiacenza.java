package it.polito.tdp.itunes.model;

public class Adiacenza {

	private Track source;
	private Track target;
	private double peso;
	
	public Adiacenza(Track source, Track target, double peso) {
		super();
		this.source = source;
		this.target = target;
		this.peso = peso;
	}

	public Track getSource() {
		return source;
	}

	public void setSource(Track source) {
		this.source = source;
	}

	public Track getTarget() {
		return target;
	}

	public void setTarget(Track target) {
		this.target = target;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "source=" + source + ", target=" + target + ", peso=" + peso;
	}
	
	
	
}
