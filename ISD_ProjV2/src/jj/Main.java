package jj;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Main {

		   static JPanel PanelLVL1;
		   static File chosenFile;
		   static JLabel LabelwybranyPlikNazwa;
		   static TextArea PoleNaWynikSzukaniaTermow;
		   static JTextField PoleNaTermy;
		   static Checkbox precyzja;
		   static JTextField EuqTargetTextF;
		   
		   boolean PRE;
		   
		   static String TermyN;
		   static String IlewystN = "";
		   
		   static int LdwTSM = 0;
		   static int IleRoznychTermow = 0;
		   
		   static DaneTablicy DTab;
		   
		   static JTable TTSM;
		   static JTable TIFIDF;
		   static JTable TEuq;
		   
	
	
	public static void RamaiUstawienia()
	{
		JFrame Program = new JFrame("ISD Projekt");
		
		Program.setSize(new Dimension(1200, 600));
		Program.setResizable(false);
		Program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		PanelLVL1 = new JPanel();
		Program.add(PanelLVL1);
		
		Content();
		
		Program.setVisible(true);
	}
	
	public static void Content()
	{
		PanelLVL1.setLayout(new BorderLayout());
		
		JPanel PanelLVL2L = new JPanel();
		PanelLVL1.add(PanelLVL2L, BorderLayout.LINE_START);
		
		JPanel PanelLVL3L = new JPanel();
		PanelLVL2L.add(PanelLVL3L);
		PanelLVL3L.setSize(new Dimension(500, 1000));
		PanelLVL3L.setMaximumSize(new Dimension(500, 1000));
		
		//Lewa czesc zawierajaca 1 cz programu (wczytywanie plikow i slow)
		
		
		PanelLVL3L.setLayout(new BoxLayout(PanelLVL3L, BoxLayout.PAGE_AXIS));
		
		JPanel WPspec = new JPanel();
		JButton wskazywaniePliku = new JButton("Wczytaj plik txt");
		wskazywaniePliku.setSize(PanelLVL3L.getWidth(), 20);
		PanelLVL3L.add(WPspec);
		WPspec.add(wskazywaniePliku);

		wskazywaniePliku.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser= new JFileChooser();
				int choice = chooser.showOpenDialog(chooser);
				if (choice != JFileChooser.APPROVE_OPTION) return;
			    chosenFile = chooser.getSelectedFile();			    
			    LabelwybranyPlikNazwa.setText("Nazwa Pliku: " + chosenFile.getName());				
			}
		});
	
		JPanel LWPNspec = new JPanel();
		LabelwybranyPlikNazwa = new JLabel("Nazwa Pliku:"); 
		PanelLVL3L.add(LWPNspec);
		LWPNspec.add(LabelwybranyPlikNazwa);
		
		JSeparator Sep11 = new JSeparator();
		Sep11.setOrientation(SwingConstants.HORIZONTAL);
		PanelLVL3L.add(Sep11, Component.CENTER_ALIGNMENT);
		
		JPanel LPTspec = new JPanel();
		JLabel LabelPolaTermow = new JLabel("Podaj termy odgradzając je przecinkami (TermA,TermB)");
		PanelLVL3L.add(LPTspec);
		LPTspec.add(LabelPolaTermow);
			
		PoleNaTermy = new JTextField(39);		
		PoleNaTermy.setMaximumSize(new Dimension(500, 40));
		JPanel PanelDlaTermow1 = new JPanel();		
		PanelLVL3L.add(PanelDlaTermow1, Component.CENTER_ALIGNMENT);
		PanelDlaTermow1.add(PoleNaTermy);
		
		JPanel BPpec = new JPanel();
		JButton BPrzeszukaj = new JButton("Skanuj plik w poszukiwaniu termów");
		PanelLVL3L.add(BPpec);
		BPpec.add(BPrzeszukaj);
		
		BPrzeszukaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				if (chosenFile.exists())
				{
					if (PoleNaTermy.getText().isEmpty())
					{
						PoleNaTermy.setText("PODAJ TERMY!");
					}
					else
						przeszukiwaniePliku();
				}
				}catch(NullPointerException brakPliku)
				{
					PoleNaWynikSzukaniaTermow.setText("Wczytaj plik!");
				}							
			}
		});
		
		PoleNaWynikSzukaniaTermow = new TextArea();
		PoleNaWynikSzukaniaTermow.setEditable(false);
		PoleNaWynikSzukaniaTermow.setMaximumSize(new Dimension(500, 40));
		JPanel PanelDlaTermow2 = new JPanel();		
		PanelLVL3L.add(PanelDlaTermow2);
		PanelDlaTermow2.add(PoleNaWynikSzukaniaTermow);
		
		precyzja = new Checkbox();
		JPanel PdlaPRE = new JPanel();
		PanelLVL3L.add(PdlaPRE);
		JLabel labelPRE = new JLabel("Precyzja?");
		PdlaPRE.add(labelPRE);
		PdlaPRE.add(precyzja);
		

		JSeparator LVL2Sep = new JSeparator();
		LVL2Sep.setOrientation(SwingConstants.VERTICAL);
		PanelLVL1.add(LVL2Sep, BorderLayout.CENTER);
		
		JPanel PanelLVL2P = new JPanel();
		PanelLVL1.add(PanelLVL2P, BorderLayout.LINE_END);
		
		//Prawa cz zawierajaca obrobke 2 cz programu
		
		JPanel P2PMenu = new JPanel();
		PanelLVL2P.setLayout(new BorderLayout());
		PanelLVL2P.add(P2PMenu, BorderLayout.NORTH);
		
		//Menu prawej strony
				
		JButton DodawanieDoTSM = new JButton("Dodaj do mTFM");
		P2PMenu.add(DodawanieDoTSM);
				
		JButton ResetowanieTSM = new JButton("Resetuj TFM");
		P2PMenu.add(ResetowanieTSM);
			
		TTSM = new JTable(DTab.daneTSM, DTab.naglowki);
		
		JPanel PL2Ptsm = new JPanel();
		PanelLVL2P.add(PL2Ptsm, BorderLayout.CENTER);
		PL2Ptsm.setLayout(new BoxLayout(PL2Ptsm, BoxLayout.PAGE_AXIS));
		
		JLabel P2PtsmLabel = new JLabel("TFM");
		PL2Ptsm.add(P2PtsmLabel);		
		PL2Ptsm.add(TTSM);
		
		ResetowanieTSM.addActionListener(new ActionListener() {

			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				LdwTSM = 0;
				
				for (int i = 0 ; i<11 ; i++) 
					for(int ii = 0 ; ii<9 ; ii++ ) 
						TTSM.setValueAt("",i,ii);
								
				PoleNaTermy.setEditable(true);				
			}
		});
		
		DodawanieDoTSM.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//dodawanie wyniku do tsm
				
				if (LdwTSM == 0)
				{
					String[] Termy = TermyN.split(Pattern.quote(","));
					int ileterm = Termy.length;
					for (int Licznik = 1; Licznik <= ileterm; Licznik++ ) 
						TTSM.setValueAt(Termy[Licznik-1], LdwTSM, Licznik);	
					LdwTSM = 1;
					PoleNaTermy.setEditable(false);
				}
					String[] Ilewyst = IlewystN.split(Pattern.quote(","));
					int ilewyst = Ilewyst.length;
					for (int Licznik = 0; Licznik <= ilewyst; Licznik++ ) {
						if (Licznik == 0) TTSM.setValueAt(chosenFile.getName(), LdwTSM, Licznik);else	
						TTSM.setValueAt(Ilewyst[Licznik-1], LdwTSM, Licznik);	
					}
					LdwTSM++;
				}});
		
		PL2Ptsm.add(Box.createRigidArea(new Dimension(15, 15)));
		
		JSeparator SeparowanieTSM = new JSeparator();
		SeparowanieTSM.setOrientation(SwingConstants.HORIZONTAL);
		PL2Ptsm.add(SeparowanieTSM);
		
		//scrolowalny panel z odpowiedziami
		
		JPanel PL2Pscrol = new JPanel();				
		PL2Pscrol.setLayout(new BoxLayout(PL2Pscrol, BoxLayout.PAGE_AXIS));
		PL2Pscrol.setPreferredSize(new Dimension( 700,1300)); 		
		JScrollPane ScrollowanyPanelnaTablice = new JScrollPane(PL2Pscrol);		
		ScrollowanyPanelnaTablice.setPreferredSize(new Dimension( 720,300));		
		PanelLVL2P.add(ScrollowanyPanelnaTablice, BorderLayout.SOUTH);
		
		JPanel TFIDFButtonPanel = new JPanel();
		PL2Pscrol.add(TFIDFButtonPanel);
		TFIDFButtonPanel.setLayout(new BoxLayout(TFIDFButtonPanel, BoxLayout.LINE_AXIS));
		
		
		JButton ButtonTSMdo1OP = new JButton("TFM > TF-IDF");
		TFIDFButtonPanel.add(ButtonTSMdo1OP);
		
		ButtonTSMdo1OP.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				obliczanieIfIdf();
			}
		});
		
		JButton ButtonResetTFIDF = new JButton("Resetuj tablice TF-IDF");
		TFIDFButtonPanel.add(ButtonResetTFIDF);
		
		ButtonResetTFIDF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				for (int i = 0; i < LdwTSM; i++)
					for (int ii = 0 ; ii < (int)IleRoznychTermow+1 ; ii++)
						TIFIDF.setValueAt("", i, ii);
			}
		});
			
		TIFIDF = new JTable(DTab.daneTFIFD, DTab.naglowki);	
		PL2Pscrol.add(TIFIDF);
		
		JPanel EuqlidesButtonPanel = new JPanel();
		PL2Pscrol.add(EuqlidesButtonPanel);
		EuqlidesButtonPanel.setLayout(new BoxLayout(EuqlidesButtonPanel, BoxLayout.LINE_AXIS));
		
	
		JLabel EuqTarget = new JLabel("Ktory dokument chcesz porownac");
		EuqlidesButtonPanel.add(EuqTarget);
		
		JPanel EuqTFfix = new JPanel();
		EuqlidesButtonPanel.add(EuqTFfix);
		EuqTargetTextF = new JTextField(2);
		EuqTFfix.setMaximumSize(new Dimension(75, 25));
		EuqTFfix.add(EuqTargetTextF);
		
		JButton EuqStartButton = new JButton("TFM > Euq");
		EuqlidesButtonPanel.add(EuqStartButton);
		
		EuqStartButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				obliczanieEuq();
			}
		});
		
		JButton EuqResetButton = new JButton("Reset Euq");
		EuqlidesButtonPanel.add(EuqResetButton);
		
		EuqResetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				for (int i = 0; i < LdwTSM; i++)
					for (int ii = 0 ; ii < (int)IleRoznychTermow+3 ; ii++)
						TEuq.setValueAt("", i, ii);				
			}
		});
		
		TEuq = new JTable(DTab.daneEuq,DTab.Euqnaglowki);
		PL2Pscrol.add(TEuq);
		
		//TODO reszta odpowiedzi programu
		
	}

	public static void przeszukiwaniePliku()
	{
		PoleNaWynikSzukaniaTermow.setText(null);
		TermyN = PoleNaTermy.getText();
		String[] Termy = TermyN.split(Pattern.quote(","));
		IleRoznychTermow = Termy.length;
		int IleTermow[]= new int[IleRoznychTermow];
		int i = 0;
		IlewystN = "";
		
		StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(chosenFile)))
        {
 
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
				
        String TextN = contentBuilder.toString();
        String[] Text = TextN.split(Pattern.quote(" "));
        
        for(String slowo : Text)
		{
        	for(String term : Termy)
        	{
        		if (precyzja.getState()) {
        			if (slowo.equals(term)) IleTermow[i] ++;
            		i++;
        		}else
        		{
        			if (slowo.contains(term)) IleTermow[i] ++;
            		i++;
        		}    		
        	}
        	i = 0;
		}
		for (i = 0; i < Termy.length; i++) {
		PoleNaWynikSzukaniaTermow.setText(PoleNaWynikSzukaniaTermow.getText()  + Termy[i] + ": " + IleTermow[i]+ "\n");		
			IlewystN=IlewystN+IleTermow[i];
			if(i < Termy.length-1) IlewystN=IlewystN+",";
		}
	}
	
	public static void obliczanieIfIdf()
	{	
		double[] ileNiePustychTermow = new double[IleRoznychTermow];
		
		for (double element : ileNiePustychTermow)
			ileNiePustychTermow[(int) element] = 0.0;
		
		//Zbieram iformacje o tym czy istnieje term w danym dokumencie
		for (int i = 1; i < LdwTSM; i++)
			for (int ii = 1 ; ii < IleRoznychTermow ; ii++)
			{
				if (TTSM.getValueAt(i,ii).toString().equals("0"))
				{}else
				{
					ileNiePustychTermow[ii-1] ++;
				}
			}
		
		//Obliczam wartosci dla tablicy IFiDF
		for (int i = 0; i < LdwTSM; i++)
			for (int ii = 0 ; ii < (int)IleRoznychTermow+1 ; ii++)
			{
				if (ii == 0 || i == 0) {
				if (ii == 0) {
					TIFIDF.setValueAt(TTSM.getValueAt(i, ii), i, ii);	
				}
				if (i == 0) {
					TIFIDF.setValueAt(TTSM.getValueAt(i, ii), i, ii);
				}
				}else 
				{
					Double wynik = Double.parseDouble((String) TTSM.getValueAt(i,ii)) * Math.log((LdwTSM-1)/ileNiePustychTermow[ii-1]);
					TIFIDF.setValueAt(Double.toString(wynik),i,ii);
				}
			}
		
	}
	
	public static void obliczanieEuq() {
		
		int indexDok = Integer.parseInt(EuqTargetTextF.getText());
		int sumaTermow = 0;
		
		
		for (int i = 0; i < LdwTSM; i++)
			for (int ii = 0 ; ii < (int)IleRoznychTermow+3 ; ii++)
			{
				if (ii == 0 || i == 0) {
				if (ii == 0) {
					TEuq.setValueAt("D" + indexDok + " - D" + i, i, ii);	
				}
				if (i == 0) {
					if ( ii < (int)IleRoznychTermow+1)
					TEuq.setValueAt(TTSM.getValueAt(i, ii), i, ii);
					else
					if (ii == (int)IleRoznychTermow+1)
					TEuq.setValueAt("Suma", i, ii);
					else
					if (ii == (int)IleRoznychTermow+2)
					TEuq.setValueAt("Sqrt(Suma)", i, ii);
				}
				}else 
				{
					if ( ii < (int)IleRoznychTermow+1)
						{
						Double wynik = Math.pow((Double.parseDouble((String) TTSM.getValueAt(indexDok,ii))-Double.parseDouble((String) TTSM.getValueAt(i,ii))), 2);
						TEuq.setValueAt(Double.toString(wynik),i,ii);
						sumaTermow += wynik;
						}
						else
						if (ii == (int)IleRoznychTermow+1)
						TEuq.setValueAt(Integer.toString(sumaTermow), i, ii);
						else
						if (ii == (int)IleRoznychTermow+2)
						{
						TEuq.setValueAt(Double.toString(Math.sqrt(sumaTermow)), i, ii);
						sumaTermow = 0;
						}
						
				}
			}
		
		
	}
	
	public static void main(String[] args) 
	{		
		RamaiUstawienia();
	}
}
