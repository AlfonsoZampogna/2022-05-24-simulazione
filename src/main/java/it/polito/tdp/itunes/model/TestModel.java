package it.polito.tdp.itunes.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		
		Genre genere = new Genre(2,"Jazz");
		m.getDeltaMassimo(genere);
	}

}
