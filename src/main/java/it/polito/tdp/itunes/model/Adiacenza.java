package it.polito.tdp.itunes.model;

public class Adiacenza {

	private Integer idTrack1;
	private String nome1;
	private Integer idTrack2;
	private String nome2;
	private double peso;
	public Adiacenza(Integer idTrack1, String nome1, Integer idTrack2, String nome2, double peso) {
		super();
		this.idTrack1 = idTrack1;
		this.nome1 = nome1;
		this.idTrack2 = idTrack2;
		this.nome2 = nome2;
		this.peso = peso;
	}
	public Integer getIdTrack1() {
		return idTrack1;
	}
	public void setIdTrack1(Integer idTrack1) {
		this.idTrack1 = idTrack1;
	}
	public String getNome1() {
		return nome1;
	}
	public void setNome1(String nome1) {
		this.nome1 = nome1;
	}
	public Integer getIdTrack2() {
		return idTrack2;
	}
	public void setIdTrack2(Integer idTrack2) {
		this.idTrack2 = idTrack2;
	}
	public String getNome2() {
		return nome2;
	}
	public void setNome2(String nome2) {
		this.nome2 = nome2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return nome1 + " , " + nome2 + " : " + peso;
	}
	

	
	
}
