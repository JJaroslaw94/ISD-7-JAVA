package jj;

import java.awt.BorderLayout;
import java.awt.Checkbox;
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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Main {

	public static JPanel PanelLVL1 = new JPanel();
		   static File chosenFile;
		   static JLabel LabelwybranyPlikNazwa;
		   static TextArea PoleNaWynikSzukaniaTermow;
		   static JTextField PoleNaTermy;
		   static Checkbox precyzja;
		   
		   boolean PRE;
		   
	
	
	public static void RamaiUstawienia()
	{
		JFrame Program = new JFrame("ISD Projekt");
		
		Program.setSize(new Dimension(1000, 500));
		//Program.setResizable(false);
		Program.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		
		//TODO Lewa czesc zawierajaca 1 cz programu (wczytywanie plikow i slow)
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
				przeszukiwaniePliku();			
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
		
		//TODO Prawa cz zawierajaca obrobke 2 cz programu
	}

	public static void przeszukiwaniePliku()
	{
		PoleNaWynikSzukaniaTermow.setText(null);
		String TermyN = PoleNaTermy.getText();
		String[] Termy = TermyN.split(Pattern.quote(","));
		int IleTermow[]= new int[Termy.length];
		int i = 0;
		
		for (int term : IleTermow)
		{
			term = 0;
		}
		
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
		for (i = 0; i < Termy.length; i++)
		PoleNaWynikSzukaniaTermow.setText(PoleNaWynikSzukaniaTermow.getText()  + Termy[i] + ": " + IleTermow[i]+ "\n");		
			
	}
	
	public static void main(String[] args) 
	{		
		RamaiUstawienia();
	}
}
