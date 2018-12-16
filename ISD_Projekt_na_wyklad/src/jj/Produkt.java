package jj;

public class Produkt {
	
	private String Nazwa;
	private String[] ListaTagow;
	private int[] ListaWystapienTagow;

	public Produkt(String Nazwa, String[] ListaTagow, int[] ListaWystapienTagow)
	{
		this.setNazwa(Nazwa);
		this.setListaTagow(ListaTagow);
		this.setListaWystapienTagow(ListaWystapienTagow);
	}
	
	public void DodajTabliceWystapien(int[] noweDane)
	{
		int index = 0;
		int[] nowaListaWystapientagow = new int[noweDane.length];
		for (int element : ListaWystapienTagow)
		{		
			element = element + noweDane[index];
			nowaListaWystapientagow[index] = element;
			index ++;
		}
		setListaWystapienTagow(nowaListaWystapientagow);
	}

	public int[] getListaWystapienTagow() {
		return ListaWystapienTagow;
	}

	public void setListaWystapienTagow(int[] listaWystapienTagow) {
		ListaWystapienTagow = listaWystapienTagow;
	}

	public String[] getListaTagow() {
		return ListaTagow;
	}

	public void setListaTagow(String[] listaTagow) {
		ListaTagow = listaTagow;
	}

	public String getNazwa() {
		return Nazwa;
	}

	public void setNazwa(String nazwa) {
		Nazwa = nazwa;
	}
	
	
	
}
