package tech.codepalace.view.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import tech.codepalace.utility.PlaceHolderTextField;
import tech.codepalace.view.panels.PanelWithBackgroundOption;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description Class for edit a new Found and lost item.
 */
@SuppressWarnings("serial")
public class NewFundsachen extends JDialog {
	
	//Variable to Know who from the Team has entered the information for the new Parking Reservation.
		private static String abkuerzungMA;
		
		//Our Panel with Background Image
		private PanelWithBackgroundOption panelWithBackgroundOption;
		
		private JPanel entriesPanel, buttonPanel, mainJPanel;
		
		private JLabel treasure, dateItemFoundJLabel, fundItemsJLabel, fundOrtJLabel, inhaberJLabel, kisteNummerJLabel, 
		kisteNameJLabel, abkuerzungMAJLabel, abkuerzungMAGeneratedJLabel;
		public PlaceHolderTextField datumItemFoundPlaceHolderTextField;
		
		/*
		 * by kisteNameJTextField will received the value when user select JComboBox or it could be modify manually.
		 */
		public JTextField fundItemsJTexfield, fundOrtJTextField, inhaberJTextField, kisteNameJTextField;
		
		
		
		public JComboBox<String> kisteNummerJComboBox;
		private String[] choices;
		
		
		public JButton saveFoundSachen, cancelFoundSachen;
		
		//JButton in case cancel
		public JButton jaJButton = new JButton("Ja"), neinJButton = new JButton("Nein");
		
		
		//Variables in case cancel edit new Entry.
		public JFrame frame = new JFrame();
		private JOptionPane optionPane = new JOptionPane();
		
		private ImageIcon icon = new ImageIcon(getClass().getResource("/img/dialogo.png"));
		
		public JDialog dialog;
		
		
		
		
		//GridBagLayout and GridGagConstrains main containt
		private GridBagLayout mainGBL;
		private GridBagConstraints mainGBC;
		
		//We use a GridBagLayout for the entries
		private GridBagLayout gbl;
	 	private GridBagConstraints gbc;
	 	
	 	
	 	
	 	public NewFundsachen(DataBaseGUI dataBaseGUI, boolean modal, String abkuerzungMA) {
	 		
	 		//We call super and the DataBaseGUI as argument so we specify that dataBaseGUI is the Object that we send that will be blocked 
	 		super(dataBaseGUI, modal);
	 		
	 		NewFundsachen.abkuerzungMA = abkuerzungMA;
	 		
	 		setSize(730, 450);
			setTitle("Neue Fundsachen Eintragen");
			
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
			
			this.datumItemFoundPlaceHolderTextField = new PlaceHolderTextField();
			
			
			
			//We add all the Elements to the Panel.
			addElementsToPanel();
		}
		
		
		private void addElementsToPanel() {
			
			//mainJPanel will be added to the panelWithBckgroundOption and it will be added to this JPanel the other elements.
			this.mainJPanel = new JPanel();
			
			//entriesPanel will contain all the edit elements.
			this.entriesPanel = new JPanel();
			
			//We set one Image to the JLabel.
			this.treasure = new JLabel(new ImageIcon(getClass().getResource("/img/treasure_bar.png")));
			
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
			
			//Start creating our JLabel and edit elements to save new Lost and found items.
			this.dateItemFoundJLabel = new JLabel("Datum:");
			this.dateItemFoundJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			this.dateItemFoundJLabel.setPreferredSize(new Dimension(180, 20));
			this.dateItemFoundJLabel.setOpaque(false);
			this.dateItemFoundJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
			this.dateItemFoundJLabel.setForeground(Color.WHITE);
			
			this.datumItemFoundPlaceHolderTextField = new PlaceHolderTextField(20);
			this.datumItemFoundPlaceHolderTextField.setPlaceholder("dd.MM.JJJJ");
			this.datumItemFoundPlaceHolderTextField.setFont(new Font("Verdana", Font.BOLD, 14));
			
			this.fundItemsJLabel = new JLabel("Fundsachen:");
			this.fundItemsJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			this.fundItemsJLabel.setPreferredSize(new Dimension(180, 20));
			this.fundItemsJLabel.setOpaque(false);
			this.fundItemsJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
			this.fundItemsJLabel.setForeground(Color.WHITE);
			
			this.fundItemsJTexfield = new JTextField(20);
			this.fundItemsJTexfield.setFont(new Font("Verdana", Font.BOLD, 14));
			
			
			
			
			this.fundOrtJLabel = new JLabel("Fundort:");
			this.fundOrtJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			this.fundOrtJLabel.setPreferredSize(new Dimension(180, 20));
			this.fundOrtJLabel.setOpaque(false);
			this.fundOrtJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
			this.fundOrtJLabel.setForeground(Color.WHITE);
			
			this.fundOrtJTextField = new JTextField(20);
			this.fundOrtJTextField.setFont(new Font("Verdana", Font.BOLD, 14));
			
			
			this.inhaberJLabel = new JLabel("Inhaber:");
			this.inhaberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			this.inhaberJLabel.setPreferredSize(new Dimension(180, 20));
			this.inhaberJLabel.setOpaque(false);
			this.inhaberJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
			this.inhaberJLabel.setForeground(Color.WHITE);
			
			
			this.inhaberJTextField = new JTextField(20);
			this.inhaberJTextField.setFont(new Font("Verdana", Font.BOLD, 14));
			
			
			this.kisteNummerJLabel = new JLabel("Kisten Nr:");
			this.kisteNummerJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			this.kisteNummerJLabel.setPreferredSize(new Dimension(180, 20));
			this.kisteNummerJLabel.setOpaque(false);
			this.kisteNummerJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
			this.kisteNummerJLabel.setForeground(Color.WHITE);
			
			
			this.choices = new String[] {"Kistenummer wählen","1-Elektro Artikel","2-Schmuck / Brillen", "3-Kleidung",
										 "4-Kosmetik / Badezimmer", "5-Bücher", "6-Briefe / Karten jegliche Art",
										 "7-Sonstiges", "8-Kiste ohne Namen / Angaben"};
			
			this.kisteNummerJComboBox = new JComboBox<String>(choices);
			this.kisteNummerJComboBox.setFont(new Font("Verdana", Font.BOLD, 14));
			
			
			
			this.kisteNameJLabel = new JLabel("Kisten Name:");
			this.kisteNameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			this.kisteNameJLabel.setPreferredSize(new Dimension(180, 20));
			this.kisteNameJLabel.setOpaque(false);
			this.kisteNameJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
			this.kisteNameJLabel.setForeground(Color.WHITE);
			
			this.kisteNameJTextField = new JTextField(20);
			this.kisteNameJTextField.setFont(new Font("Verdana", Font.BOLD, 14));
			
			this.abkuerzungMAJLabel = new JLabel("Kürzel MA:");
			this.abkuerzungMAJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			this.abkuerzungMAJLabel.setPreferredSize(new Dimension(180, 20));
			this.abkuerzungMAJLabel.setOpaque(false);
			this.abkuerzungMAJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
			this.abkuerzungMAJLabel.setForeground(Color.WHITE);
			
			
			this.abkuerzungMAGeneratedJLabel = new JLabel();
			

			this.abkuerzungMAGeneratedJLabel.setPreferredSize(new Dimension(300, 20));
			this.abkuerzungMAGeneratedJLabel.setOpaque(false);
			this.abkuerzungMAGeneratedJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
			this.abkuerzungMAGeneratedJLabel.setText(getAbkuerzungMA());
			
		
			//We position the elements
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbl.setConstraints(this.dateItemFoundJLabel, gbc);
			this.entriesPanel.add(this.dateItemFoundJLabel);
			
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.insets = new Insets(0, 5, 0, 0);
			gbl.setConstraints(datumItemFoundPlaceHolderTextField, gbc);
			this.entriesPanel.add(datumItemFoundPlaceHolderTextField);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.insets = new Insets(10, 0, 0, 0);
			gbl.setConstraints(fundItemsJLabel, gbc);
			this.entriesPanel.add(fundItemsJLabel);
			
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1;
			gbc.gridy = 1;
			gbc.insets = new Insets(10, 5, 0, 0);
			gbl.setConstraints(fundItemsJTexfield, gbc);
			this.entriesPanel.add(fundItemsJTexfield);
			
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.insets = new Insets(10, 0, 0, 0);
			gbl.setConstraints(fundOrtJLabel, gbc);
			this.entriesPanel.add(this.fundOrtJLabel);
			
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1;
			gbc.gridy = 2;
			gbc.insets = new Insets(10, 5, 0, 0);
			gbl.setConstraints(fundOrtJTextField, gbc);
			this.entriesPanel.add(this.fundOrtJTextField);
			
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.insets = new Insets(10, 0, 0, 0);
			gbl.setConstraints(inhaberJLabel, gbc);
			this.entriesPanel.add(inhaberJLabel);
			
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1;
			gbc.gridy = 3;
			gbc.insets = new Insets(10, 5, 0, 0);
			gbl.setConstraints(inhaberJTextField, gbc);
			this.entriesPanel.add(inhaberJTextField);
			
	
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbc.insets = new Insets(10, 0, 0, 0);
			gbl.setConstraints(kisteNummerJLabel, gbc);
			this.entriesPanel.add(kisteNummerJLabel);
			
	
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1;
			gbc.gridy = 4;
			gbc.insets = new Insets(10, 5, 0, 0);
			gbl.setConstraints(kisteNummerJComboBox, gbc);
			this.entriesPanel.add(kisteNummerJComboBox);
			
			
			gbc.gridx = 0;
			gbc.gridy = 5;
			gbc.insets = new Insets(10, 0, 0, 0);
			gbl.setConstraints(kisteNameJLabel, gbc);
			this.entriesPanel.add(kisteNameJLabel);
			
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.weightx = 0.5;
			gbc.gridx = 1;
			gbc.gridy = 5;
			gbc.insets = new Insets(10, 5, 0, 0);
			gbl.setConstraints(kisteNameJTextField, gbc);
			this.entriesPanel.add(kisteNameJTextField);
			
			
			
			gbc.gridx = 0;
			gbc.gridy = 6;
			gbc.insets = new Insets(10, 0, 0, 0);
			gbl.setConstraints(abkuerzungMAJLabel, gbc);
			this.entriesPanel.add(abkuerzungMAJLabel);
			
			
			   
			gbc.gridx = 1;
			gbc.gridy = 6;
			gbc.insets = new Insets(10, 5, 0, 0);
			gbl.setConstraints(abkuerzungMAGeneratedJLabel, gbc);
			this.entriesPanel.add(abkuerzungMAGeneratedJLabel);
			
			this.saveFoundSachen = new JButton("Speichern");
			this.saveFoundSachen.setFocusable(true);
			this.cancelFoundSachen = new JButton("Abbrechen");
			
			this.buttonPanel = new JPanel(new BorderLayout());
			this.buttonPanel.setOpaque(false);
			
			this.buttonPanel.add(this.saveFoundSachen, BorderLayout.EAST);
			this.buttonPanel.add(this.cancelFoundSachen, BorderLayout.WEST);
			
			
			mainGBC.gridx = 0;
			mainGBC.gridy = 0;
			mainGBL.setConstraints(treasure, mainGBC);
			this.mainJPanel.add(treasure);
			
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
			
			
			//Finally we add the mainJPanel to the Contentpanel.
			this.panelWithBackgroundOption.add(this.mainJPanel);


			
			
			
		}
		
		
		//Method to get the information who from the team is typing the Data.
		private static String getAbkuerzungMA() {
			return abkuerzungMA;
		}
		
		
		
		/**
		 * @description Method to confirm application closure
		 */
		public void confirmClose() {

			  
			 
			optionPane.setMessage("Wollen Sie den Eintrag Abbrechen?");
			 optionPane.setMessageType(JOptionPane.OK_CANCEL_OPTION);
			 optionPane.setIcon(icon);
			
			
			 dialog = optionPane.createDialog(frame, "Eintrag Abbrechen?");
			 
			 optionPane.setOptions(new Object[] {this.neinJButton, this.jaJButton});
			 
			
			 dialog.setVisible(true);
			 
	
				
		}
		
		

}
