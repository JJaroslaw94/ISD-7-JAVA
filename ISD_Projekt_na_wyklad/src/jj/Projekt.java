package jj;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Projekt {
	
	public static CardLayout przelacznik;

	public static JPanel _1stLayer;
	public static JPanel PanelGridow;
	public static JPanel GridPanel2;
	
	public static JLabel nazwaPrzedmiotuJakoNaglowek;
	public static JLabel PolecaneLabel;
	
	public static String wyszukiwanaFraza = "";
	
	public static String[] listaProduktow;
	public static String[] listaTagow;
	public static String[] NazwyProduktow;
	public static String[] listaWyszukan;
	
	public static List<String> ListaProduktow = new ArrayList<String>();
	public static List<String> ListaWyszukan = new ArrayList<String>();
	
	public static List<String> ListaTagow = new ArrayList<String>();
	
	public static List<Produkt> ListaWystapienProduktow = new ArrayList<Produkt>();
	
	public static File plikWyszukiwan;
	
	public static void frame()
	{
		JFrame Program = new JFrame("ISD Projekt");
		
		Program.setSize(new Dimension(600, 400));
		Program.setResizable(false);
		Program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		_1stLayer = new JPanel(new CardLayout());
		Program.add(_1stLayer);
		
		Content();
		
		Program.setVisible(true);		
	}
	
	public static void Content()
	{
		//Wczytanie pliku dostepnych kwiatow
		BufferedReader br = null;
		try {
			File plikKwiatow = new File("kwiaty.txt");			
			br = new BufferedReader(new FileReader(plikKwiatow));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} 
				  
		
		BufferedReader br2 = null;
		try {
			plikWyszukiwan = new File("wyszukaneDane.txt");			
			br2 = new BufferedReader(new FileReader(plikWyszukiwan));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} 
		
		
		String st; 
		  
		try {
			while ((st = br.readLine()) != null) {
					ListaProduktow.add(st);				   
				  }
		} catch (IOException e1) {} 			
		try {
			while ((st = br2.readLine()) != null) {
					ListaWyszukan.add(st);
				  }
		} catch (IOException e1) {}
		
		listaProduktow = ListaProduktow.toArray(new String[0]);
		
		listaTagow = new String[ListaProduktow.size()];
		NazwyProduktow = new String[ListaProduktow.size()];
				
		for (String Przedmiot : listaProduktow)
		{ 
			int i = Arrays.asList(listaProduktow).indexOf(Przedmiot);
			String[] wydzielenieNazwy = Przedmiot.split(Pattern.quote("."));
			NazwyProduktow[i] = wydzielenieNazwy[0];
			listaTagow[i] = wydzielenieNazwy[1];
		}
		
		
		InterpreterPlikow();
		
		
		JPanel _2ndLayer1stPanel = new JPanel();
		_1stLayer.add(_2ndLayer1stPanel, "StronaStartowa");
		
		JPanel _2ndLayer2ndPanel = new JPanel();
		_1stLayer.add(_2ndLayer2ndPanel, "StronaWyszukiwarki");
		
		przelacznik = (CardLayout)(_1stLayer.getLayout());
		przelacznik.show(_1stLayer, "StronaStartowa");
		
		//TODO content 1 strony
		
		_2ndLayer1stPanel.setLayout(new BoxLayout(_2ndLayer1stPanel, BoxLayout.Y_AXIS));
		
		_2ndLayer1stPanel.add(Box.createRigidArea(new Dimension(100, 100)));
		
		JPanel Wyszukiwarka = new JPanel();
		_2ndLayer1stPanel.add(Wyszukiwarka);
		
		
		
		JTextField SearchBar1 = new JTextField(20);
		Wyszukiwarka.add(SearchBar1);
		
		JButton SearchBarButton1 = new JButton("Szukaj");
		Wyszukiwarka.add(SearchBarButton1);
		SearchBarButton1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				przelacznik.show(_1stLayer, "StronaWyszukiwarki");
				wyszukiwanaFraza = SearchBar1.getText();
				wyswietlKwiaty();
			}
		});
		
		//TODO content 2 strony
		
		_2ndLayer2ndPanel.setLayout(new BoxLayout(_2ndLayer2ndPanel, BoxLayout.Y_AXIS));
		
		_2ndLayer2ndPanel.add(Box.createRigidArea(new Dimension(100, 100)));
		
		JPanel WyszukiwarkaZWynikiem = new JPanel();
		_2ndLayer2ndPanel.add(WyszukiwarkaZWynikiem);
				
		JTextField SearchBar2 = new JTextField(40);
		WyszukiwarkaZWynikiem.add(SearchBar2);
		
		JButton SearchBarButton2 = new JButton("Szukaj");
		WyszukiwarkaZWynikiem.add(SearchBarButton2);
		SearchBarButton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				wyszukiwanaFraza = SearchBar2.getText();	
				PanelGridow.removeAll();
				PanelGridow.revalidate();
				PanelGridow.repaint();
				wyswietlKwiaty();
			}
		});
		
		PanelGridow = new JPanel();
		PanelGridow.setLayout(new GridLayout(10, 4));
		WyszukiwarkaZWynikiem.add(PanelGridow);
		
		JPanel _3rdLayer1stPanel = new JPanel();
		_1stLayer.add(_3rdLayer1stPanel, "Po_wybraniu_kwiata");
		_3rdLayer1stPanel.setLayout(new BoxLayout(_3rdLayer1stPanel, BoxLayout.Y_AXIS));
		
		nazwaPrzedmiotuJakoNaglowek = new JLabel("");
		_3rdLayer1stPanel.add(nazwaPrzedmiotuJakoNaglowek);
		nazwaPrzedmiotuJakoNaglowek.setFont(new Font("Arial", Font.BOLD, 20));
		
		PolecaneLabel = new JLabel("Polecamy również: ");
		_3rdLayer1stPanel.add(PolecaneLabel);
		PolecaneLabel.setVisible(false);
		
		GridPanel2 = new JPanel();
		GridPanel2.setLayout(new GridLayout(4, 2));
		_3rdLayer1stPanel.add(GridPanel2);
		
		JButton ButtonPowrotu = new JButton("Nowy");
		ButtonPowrotu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ListaWystapienProduktow = null;
				ListaWystapienProduktow = new ArrayList<Produkt>();
				InterpreterPlikow();
				przelacznik.show(_1stLayer, "StronaWyszukiwarki");		
				PanelGridow.removeAll();
				PanelGridow.revalidate();
				PanelGridow.repaint();
			}
		});
		_3rdLayer1stPanel.add(ButtonPowrotu);
		
		
	}
	
	public static void wyswietlKwiaty()
	{	
		wyszukiwanaFraza = wyszukiwanaFraza.toUpperCase().replace('Ż','Z');
		wyszukiwanaFraza = wyszukiwanaFraza.toUpperCase().replace('Ó','O');
		wyszukiwanaFraza = wyszukiwanaFraza.toUpperCase().replace('Ę','E');		
		wyszukiwanaFraza = wyszukiwanaFraza.toUpperCase().replace('Ł','L');
		wyszukiwanaFraza = wyszukiwanaFraza.toUpperCase().replace('Ą','A');  // Zamieniam Polskie znaki poniewaz dane zapisuje bez nich
		
		String[] PodzielonaWF = wyszukiwanaFraza.split(Pattern.quote(" "));  // Dziele slowo podane przy wyszukiwaniu na termy
		
		int x = 0;
		int y = 0;
		int[][] tablicaTFM = new int[PodzielonaWF.length][ListaWystapienProduktow.size()]; // Tworzy array na podstawie ilosci termow i produktow
		
		for (int x1 = 0; x1 < tablicaTFM.length; x1++) {
		    for (int y1 = 0; y1 < tablicaTFM[x1].length; y1++) { 						// Inicjuje Array
		    	tablicaTFM[x1][y1] = 0;
		    }}
			
		String[] NaglowkiTFM = new String[ListaWystapienProduktow.size()];
		String[] ListaTagow;
		int[] ListaWystapien;
					
			for (Produkt produkt : ListaWystapienProduktow)
			{
				x = 0;
				ListaTagow = produkt.getListaTagow();
				ListaWystapien = produkt.getListaWystapienTagow();
				
				for (String slowo : PodzielonaWF) {				
					int index = 0;
					for (String nazwa : ListaTagow) //dla kazdego elementu z listy obiektow produktu
					{
						
						if (slowo.equals(nazwa))
						{
							tablicaTFM[x][y] = ListaWystapien[index];	//dodaje dane do macierzy TFM		 						
						}
						index ++;
					}
					x++;
				}	
				NaglowkiTFM[y] = produkt.getNazwa(); // zapisuje naglowek osobno
				y++;
			}
			
		int[][] TablicaEuq = new int[PodzielonaWF.length][ListaWystapienProduktow.size()];
		int[] OdleglosciEuq = new int[ListaWystapienProduktow.size()];
		
		for (Produkt produkt : ListaWystapienProduktow) // dla kazdego produktu z listy obiektow
		{
			wychodzexD:	// tag dla break'a
			for (String tag: produkt.getListaTagow())  // dla kazdego tagu z listy tagow
			{
				for(String Fraza : PodzielonaWF) // oraz dla kazdego slowa podanego podczas wyszukiwania
				{
					if (tag.equals(Fraza)) {   // jezeli tag jest rowny frazie
						JButton przycisk = new JButton(String.valueOf(produkt.getNazwa())); // tworzy przycisk
						przycisk.addActionListener(new ActionListener() {					// oraz dodaje ActionListenera
							//
							@Override
							public void actionPerformed(ActionEvent e) {
								nazwaPrzedmiotuJakoNaglowek.setText(produkt.getNazwa());
								
								Writer output;			// Dodaje wpis do piku wyszukaneDane.txt
								try {
									String tagowanie = "";
									for (String slowo : PodzielonaWF)
									{
									tagowanie = tagowanie + slowo + ",";								
									}
									tagowanie = tagowanie.substring(0, tagowanie.length() -1);
									output = new BufferedWriter(new FileWriter(plikWyszukiwan, true));
									output.append(tagowanie + "." + produkt.getNazwa());
									output.append(System.getProperty("line.separator"));
									output.close();
								} catch (IOException e1) {}  
								
								
								int indexSzukanegoTaga = Arrays.asList(NaglowkiTFM).indexOf(produkt.getNazwa()); // zwraca indeks produktu
								
								for (int x = 0; x < tablicaTFM.length; x++) {				// tworzy macierz przygotowojaca dane dla odleglosci Euqlidesowych
								    for (int y = 0; y < tablicaTFM[x].length; y++) {		// ten array jest wyswietlany razem z wynikiem(reklamami)
								    	TablicaEuq[x][y] = (int) Math.pow(tablicaTFM[x][indexSzukanegoTaga] - tablicaTFM[x][y], 2);								    
								    }
								}
								
								double[] WlasciweWartosciEuq = new double[OdleglosciEuq.length];
								int index = 0;
								
								
								for (int x = 0; x < tablicaTFM.length; x++) {			// sumuje odległości
								    for (int y = 0; y < tablicaTFM[x].length; y++) {
								    	OdleglosciEuq[y] = OdleglosciEuq[y] + TablicaEuq[x][y];								    	
								    }
								}
								
								double max = 0;
								
								for (double wartoscEuq : WlasciweWartosciEuq) {			// pierwiastkuje zsumowane odleglosci tworzac miary odleglosci Euqlidesa
									
									wartoscEuq = Math.sqrt(new Double(OdleglosciEuq[index]));
									if (max<wartoscEuq) max = wartoscEuq;
									WlasciweWartosciEuq[index] = wartoscEuq;
									index ++;								    
								}
								max ++;
								
								index = 0;		
								double ii;
								
								double reklamy[] = {max,max,max};
		
								int[] indexyReklam = {-1,-1,-1};
								
								
								// W tym bloku maja zostac wybrane produkty do zareklamowania na podstawie ich odleglosci od wskazanego produktu 3 najblizesze zostana reklamowane
								stop:
								for (double i : WlasciweWartosciEuq)
								{
									ii = i;
									if (OdleglosciEuq[index] > 4)
									{
										if (ii < reklamy[0])
										{
											reklamy[2] = reklamy[1];
											reklamy[1] = reklamy[0];
											reklamy[0] = ii;
											indexyReklam[2] = indexyReklam[1];
											indexyReklam[1] = indexyReklam[0];
											indexyReklam[0] = index;
											//break stop;
											index ++;
											continue stop;
										}
										
										if (ii < reklamy[1])
										{
											reklamy[2] = reklamy[1];
											reklamy[1] = ii;
											indexyReklam[2] = indexyReklam[1];
											indexyReklam[1] = index;
											index ++;
											//break stop;
											continue stop;
										}
										
										if (ii < reklamy[2])
										{
											reklamy[2] = ii;
											indexyReklam[2] = index;
											index ++;
											//break stop;
											continue stop;
										}
																												
									}
									index ++;
									
								}

								GridPanel2.removeAll();			//Resetuje panel przed dodaniem do niego nowych elementow
								GridPanel2.revalidate();
								GridPanel2.repaint();
								
								boolean reklamowac = false;
								
								for (int i : indexyReklam)		// Kazdy element array'a indexyReklam jest sprawdzany
								{								// Jezeli 1szy element jest rowny -1 nie zostana podane zadne reklamy
									if (i != -1)
									{
									JLabel ReklamowanyProdukt = new JLabel(NaglowkiTFM[i]);
									GridPanel2.add(ReklamowanyProdukt);
									reklamowac = true;		    // Jezeli istnieje co najmniej 1 reklama dodaje Label z "Polecamy rowniez"
									}
								}
								
								if (reklamowac)
									PolecaneLabel.setVisible(true);
								else
									PolecaneLabel.setVisible(false);
								
								JFrame rezultat = new JFrame("Euqlides");  //Dodatkowo dodalem nowe okienko z wczesniej wspomnianym arrayem
																		   //jako tabelka przedstawiajaca wartosci odleglosci
								String[][] MEuqKonw = new String[PodzielonaWF.length][ListaWystapienProduktow.size()];
								
								for (int x = 0; x < MEuqKonw.length; x++) {
								    for (int y = 0; y < MEuqKonw[x].length; y++) {
								    	MEuqKonw[x][y] = Integer.toString(TablicaEuq[x][y]);
								    }
								    }
								
								JTable TEuq = new JTable(MEuqKonw, NaglowkiTFM);
								
								rezultat.setSize(new Dimension(500, 150));
								rezultat.add(TEuq);
								
								rezultat.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
								rezultat.setVisible(true);
								
								przelacznik.show(_1stLayer, "Po_wybraniu_kwiata");				
							}
						});
						PanelGridow.add(przycisk);											// dodaje przycisk do panelu gridow
						break wychodzexD;				// aby uniknac duplikatow wylamuje sie z 2 petli na raz ( do tagu )
					}	
				}
			}
		}		
	}
	
	public static void InterpreterPlikow()
	{
		listaWyszukan = ListaWyszukan.toArray(new String[0]);
				
				//Wypelnia liste Produktow razem z wystepowaniami termow
				for (String Przedmiot : listaWyszukan)
				{
					String[] wydzielenieNazwy = Przedmiot.split(Pattern.quote("."));
					int indexProduktu = Arrays.asList(NazwyProduktow).indexOf(wydzielenieNazwy[1]);
					if ( indexProduktu == -1) indexProduktu = 0;
					String[] tagiWyszukiwarki = wydzielenieNazwy[0].split(Pattern.quote(","));
					String[] listaTagowDanegoPrzedmiotu = listaTagow[indexProduktu].split(Pattern.quote(","));
					int[] listaTagowDanegoPrzedmiotuILOSC = new int[listaTagowDanegoPrzedmiotu.length];
					
					for(int index : listaTagowDanegoPrzedmiotuILOSC)
					{
						index = 0;
					}
					
					for (String tag : tagiWyszukiwarki)
					{
						for (String lp : listaTagowDanegoPrzedmiotu)
						{
						if (tag.toUpperCase().equals(lp)) {
							int indexTaguProduktu = Arrays.asList(listaTagowDanegoPrzedmiotu).indexOf(lp);
							listaTagowDanegoPrzedmiotuILOSC[indexTaguProduktu] ++;					
						}
						}
					}
					
					Produkt nowy = new Produkt(NazwyProduktow[indexProduktu],listaTagowDanegoPrzedmiotu,listaTagowDanegoPrzedmiotuILOSC);
					
					boolean istnieje = false;
					
					for (Produkt sprawdzanie : ListaWystapienProduktow)
					{
						
						if (sprawdzanie.getNazwa().equals(nowy.getNazwa())) {
							istnieje = true;
							sprawdzanie.DodajTabliceWystapien(nowy.getListaWystapienTagow());
						}				
					}
					if (istnieje) {}else
					{
					ListaWystapienProduktow.add(nowy);
					}
					nowy = null;
					
					System.out.println(indexProduktu);
				}
				
				// Po dodaniu poprzednich Produktow uzupelniam dostepne produkty o te co nie mialy zadnych wystapionych termow
				for (String nazwa : NazwyProduktow)
				{
					boolean istnieje = false;
					int indexProduktu = Arrays.asList(NazwyProduktow).indexOf(nazwa);
					for (Produkt sprawdzanie : ListaWystapienProduktow)
					if (nazwa.equals(sprawdzanie.getNazwa())){
						istnieje = true;
					}
					if (istnieje) System.out.println("Tak " + nazwa + " istnieje");
					else 
						{
						System.out.println("nie " + nazwa + " nie istnieje");
						String[] listaTagowDanegoPrzedmiotu = listaTagow[indexProduktu].split(Pattern.quote(","));
						int[] ListaWystapienTagow = new int[listaTagowDanegoPrzedmiotu.length];
						for (int liczba : ListaWystapienTagow)
						{
							liczba = 0; 
						}
						Produkt nowy = new Produkt(nazwa, listaTagowDanegoPrzedmiotu, ListaWystapienTagow);
						ListaWystapienProduktow.add(nowy);
						nowy = null;
						System.out.println( nazwa + " utworzony");
						}
			}
	}
	
	public static void main(String[] args) {
		frame();
	}

}
