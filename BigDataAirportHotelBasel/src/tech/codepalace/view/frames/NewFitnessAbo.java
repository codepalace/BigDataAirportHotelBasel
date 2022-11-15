package tech.codepalace.view.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import tech.codepalace.utility.PlaceHolderTextField;
import tech.codepalace.view.buttons.MyButton;
import tech.codepalace.view.panels.PanelWithBackgroundOption;

@SuppressWarnings("serial")
public class NewFitnessAbo extends JDialog {
	
	//Variable for the User Abbreviation Name
	private static String abkuerzungMA;
	
	//Our Panel with Background Image
	private PanelWithBackgroundOption panelWithBackgroundOption;
			
	private JPanel entriesPanel, buttonPanel, mainJPanel;
	
	private JLabel fitnessGirl, nameJLabel, eintrittsdatumJLabel, 
					anzahlDerMonatJLabel,  betragJLabel, betragGeneratedJLabel, firmaJLabel, bemerkungenJLabel;
	
	
	public JTextField nameJTextfield, firmaJTextField, bemerkungenJTextField;

	
	public PlaceHolderTextField eintrittsdatumPlaceHolderTextField;
	
	public JComboBox<String>anzahlDerMonat;
	
	private String[] choices;
	
	// GridBagLayout and GridGagConstrains main containt
	private GridBagLayout mainGBL;
	private GridBagConstraints mainGBC;

	// We use a GridBagLayout for the entries
	private GridBagLayout gbl;
	private GridBagConstraints gbc;

	// Variables for the Custom Windows Title
	private JPanel infoJPanel;
	private JLabel infoJLabel;
	
	private MyButton saveNewFitnessAbo, cancelNewFitnessAbo;
	
	private String fitnessID = "AHF";
	
	private int tableCounter;
	

	public NewFitnessAbo(DataBaseGUI dataBaseGUI, boolean modal, String abkuerzungMA, int tableCounter) {
		
		//We call super and the DataBaseGUI as argument so we specify that dataBaseGUI is the Object that we send that will be blocked 
 		super(dataBaseGUI, modal);
 		
 		NewFitnessAbo.abkuerzungMA = abkuerzungMA;
 		
 		//We set the value of tableCounter(The total rows in the JTable(fitnessAboTable) + 1
 	    //This will be used to set the fitnessID
 		this.tableCounter = tableCounter + 1;
 		
 		//We set the fitnessID applying also the value of tableCounter to generate a new fitnessID
 		this.fitnessID += this.tableCounter;
 		
 		
 		setSize(730, 370);
 		
 		setTitle("Neues Fitness-Abo");
 		
 		setUndecorated(true);
 		
 		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
 		setResizable(false);
 		
 	// addComponentListener to center the window again.
 	this.addComponentListener(new java.awt.event.ComponentAdapter() {
 		
 		public void componentMoved(java.awt.event.ComponentEvent evt) {
 			formComponentMoved(evt);
 		}
 				
 	});
 				
 				//We call the init(), Method
 				init();
 				
 				
 	  
	}
	
	
	/**
	 * 
	 * @param evt
	 * @description method to re-center the window in case the user tries to drag the window
	 */
	private void formComponentMoved(java.awt.event.ComponentEvent evt) {                                    
	       this.setLocationRelativeTo(null);
	    }   
	
	
	private void init() {
		
		this.panelWithBackgroundOption = new PanelWithBackgroundOption();
		
		this.panelWithBackgroundOption.setImage("/img/backgroundframe.jpg");
		
		setContentPane(this.panelWithBackgroundOption);
		
		setIconImage(new ImageIcon(getClass().getResource("/img/iconoHotel.png")).getImage());
		
		

		//We add all the Elements to the Panel.
		addElementsToPanel();
	}
	
	
	
	private void addElementsToPanel() {
		
		//mainJPanel will be added to the panelWithBckgroundOption and it will be added to this JPanel the other elements.
		this.mainJPanel = new JPanel();
		
		//entriesPanel will contain all the edit elements.
		this.entriesPanel = new JPanel();
		
		this.fitnessGirl = new JLabel(new ImageIcon(getClass().getResource("/img/fitnessgirl4.png")));
		
		this.buttonPanel = new JPanel(new BorderLayout());
		this.buttonPanel.setOpaque(false);
		
		
		
		
		this.infoJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.infoJLabel = new JLabel(this.getTitle());
		this.infoJLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		this.infoJLabel.setForeground(Color.WHITE);
		this.infoJPanel.setBackground(Color.GRAY);
		this.infoJPanel.add(this.infoJLabel);
		
		this.nameJLabel = new JLabel("Name:");
		this.nameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.nameJLabel.setPreferredSize(new Dimension(180, 20));
		this.nameJLabel.setOpaque(false);
		this.nameJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.nameJLabel.setForeground(Color.white);
		
		this.nameJTextfield = new JTextField(20);
		this.nameJTextfield.setFont(new Font("Verdana", Font.BOLD, 14));
		
		this.eintrittsdatumJLabel = new JLabel("Eintrittsdatum:");
		this.eintrittsdatumJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.eintrittsdatumJLabel.setPreferredSize(new Dimension(180, 20));
		this.eintrittsdatumJLabel.setOpaque(false);
		this.eintrittsdatumJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.eintrittsdatumJLabel.setForeground(Color.white);
		
		this.eintrittsdatumPlaceHolderTextField = new PlaceHolderTextField(20);
		this.eintrittsdatumPlaceHolderTextField.setPlaceholder("dd.MM.JJJJ");
		this.eintrittsdatumPlaceHolderTextField.setFont(new Font("Verdana", Font.BOLD, 14));
		
		
		
		this.anzahlDerMonatJLabel = new JLabel("Anzahl der Monat:");
		this.anzahlDerMonatJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.anzahlDerMonatJLabel.setPreferredSize(new Dimension(180, 20));
		this.anzahlDerMonatJLabel.setOpaque(false);
		this.anzahlDerMonatJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.anzahlDerMonatJLabel.setForeground(Color.white);
		
		this.choices = new String[] {"1 Monat", "3 Monate", "6 Monate", "12 Monate"};
		
		this.anzahlDerMonat = new JComboBox<String>(choices);
		this.anzahlDerMonat.setFont(new Font("Verdana", Font.BOLD, 14));
		
		this.betragJLabel = new JLabel("Betrag:");
		this.betragJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.betragJLabel.setPreferredSize(new Dimension(180, 20));
		this.betragJLabel.setOpaque(false);
		this.betragJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.betragJLabel.setForeground(Color.white);
		
		
		this.betragGeneratedJLabel = new JLabel("0.00 CHF");
		this.betragGeneratedJLabel.setPreferredSize(new Dimension(300, 20));
		this.betragGeneratedJLabel.setOpaque(false);
		this.betragGeneratedJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		
		this.firmaJLabel = new JLabel("Firma:");
		this.firmaJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.firmaJLabel.setPreferredSize(new Dimension(180, 20));
		this.firmaJLabel.setOpaque(false);
		this.firmaJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.firmaJLabel.setForeground(Color.white);
		
		this.firmaJTextField = new JTextField(20);
		this.firmaJTextField.setFont(new Font("Verdana", Font.BOLD, 14));
		
		this.bemerkungenJLabel = new JLabel("Bemerkungen:");
		this.bemerkungenJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.bemerkungenJLabel.setPreferredSize(new Dimension(180, 20));
		this.bemerkungenJLabel.setOpaque(false);
		this.bemerkungenJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.bemerkungenJLabel.setForeground(Color.white);
		
		this.bemerkungenJTextField = new JTextField(20);
		this.bemerkungenJTextField.setFont(new Font("Verdana", Font.BOLD, 14));
		
		
		
		
		
		//GridBagLayout for the mainJPanel
		this.mainGBL = new GridBagLayout();
		
		//GridBagConstraints for the mainJPanel
		this.mainGBC = new GridBagConstraints();
		
		//GridBagLayout for the entriesPanel
		this.gbl = new GridBagLayout();
		
		//GridBagConstraints for the entriesPanel
		this.gbc = new GridBagConstraints();
		
		//setLayout to entriesPanel the GridBagLayout
		this.entriesPanel.setLayout(gbl);
		this.entriesPanel.setOpaque(false);
		
		//setLayout to the mainJPanel the GridBagLayout
		this.mainJPanel.setLayout(mainGBL);
		this.mainJPanel.setOpaque(false);
		
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbl.setConstraints(this.nameJLabel, gbc);
		this.entriesPanel.add(this.nameJLabel);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 5, 0, 0);
		gbl.setConstraints(this.nameJTextfield, gbc);
		this.entriesPanel.add(this.nameJTextfield);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbl.setConstraints(this.eintrittsdatumJLabel, gbc);
		this.entriesPanel.add(this.eintrittsdatumJLabel);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 5, 0, 0);
		gbl.setConstraints(this.eintrittsdatumPlaceHolderTextField, gbc);
		this.entriesPanel.add(this.eintrittsdatumPlaceHolderTextField);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbl.setConstraints(this.anzahlDerMonatJLabel, gbc);
		this.entriesPanel.add(this.anzahlDerMonatJLabel);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0.5;
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 5, 0, 0);
		gbl.setConstraints(this.anzahlDerMonat, gbc);
		this.entriesPanel.add(this.anzahlDerMonat);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbl.setConstraints(this.betragJLabel, gbc);
		this.entriesPanel.add(this.betragJLabel);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(10,5, 0, 0);
		gbl.setConstraints(this.betragGeneratedJLabel, gbc);
		this.entriesPanel.add(this.betragGeneratedJLabel);
		
		
		this.saveNewFitnessAbo = new MyButton("/img/btn_speichern.png");
	
		this.cancelNewFitnessAbo = new MyButton("/img/btn_abbrechen.png");
	
		this.buttonPanel.add(this.saveNewFitnessAbo, BorderLayout.EAST);
		this.buttonPanel.add(this.cancelNewFitnessAbo, BorderLayout.WEST);
		
		
		
		
		mainGBC.gridx = 0;
		mainGBC.gridy = 0;
		mainGBL.setConstraints(fitnessGirl, mainGBC);
		this.mainJPanel.add(fitnessGirl);
		
		
		mainGBC.gridx = 1;
		mainGBC.gridy = 0;
		mainGBL.setConstraints(this.entriesPanel, mainGBC);
		
		//We add the entriesPanel to the mainJPanel
		this.mainJPanel.add(this.entriesPanel);
		
		
		mainGBC.gridx = 0;
		mainGBC.gridy = 1;
		mainGBC.fill = GridBagConstraints.HORIZONTAL;
		mainGBC.gridwidth = 2;
		mainGBL.setConstraints(buttonPanel, mainGBC);
		
		//We add the JButton s to the mainJPanel
		this.mainJPanel.add(buttonPanel);
		
		this.panelWithBackgroundOption.setLayout(new BorderLayout());
		
		this.panelWithBackgroundOption.add(infoJPanel, BorderLayout.NORTH);
		//Finally we add the mainJPanel to the Contentpanel.
		this.panelWithBackgroundOption.add(this.mainJPanel, BorderLayout.CENTER);
		
		
	}

}
