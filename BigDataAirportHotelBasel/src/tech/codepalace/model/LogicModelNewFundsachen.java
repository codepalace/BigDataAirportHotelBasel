package tech.codepalace.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import tech.codepalace.dao.DAOFundsachen;
import tech.codepalace.dao.DaoException;
import tech.codepalace.dao.DaoFundsachenImpl;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;
import tech.codepalace.view.frames.NewFundsachen;


/**
 * @description Logic Class for the NewFundsachen GUI.
 * @author Antonio Estevez Gonzalez
 *
 */
public class LogicModelNewFundsachen extends LogicModel {
	
	private DataBaseGUI dataBaseGUI;
	private NewFundsachen newFundsachen; 
	private UserAHB userAHB;
	private Loading loading;
	
	public JDialog  errorUnvollstaendigenDaten;
	
	private JButton okButtonErrorDateLostAndFound  = new JButton("OK");
	
	private JLabel messageErrorDateLostAndFound;
	
	private JPanel panelErrorDateLostAndFound;
	
	
	private Object[] optionButtonErrorDateLostAndFound = {this.okButtonErrorDateLostAndFound};
	
	private ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	//Variable to check if the user has pressed clothing NewFundSachen.
	private boolean closingNewFundsachen = false;
	
	//Variable to indicate if the Date Format is correct or not.
	private boolean errorDateFormat = false;
	
	//Variables to indicate if all the JTextField are good fill out.
	private boolean foundItemCompleted = false, foundPlaceCompleted = false, ownerCompleted = false, boxnameCompleted = false; 
	
	//Variable Object Fundgegenstand to save the information Lost And Found items.
	protected Fundgegenstand fundgegenstand;
	
	//Varialbe LocalDate for Lost And Found Items needed to convert later for saving the right Date to the Database Derby. 
	private  LocalDate lostAndFoundLocalDate;
	
	//DateTimeFormatter to set the Date Format we need.
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	
	//Variable Date for saving the Date(sql.date) information as Date for the Lost And Found Items
	private Date lostAndFoundDate;
	
	//Variable for the KisteNummer
	private int kisteNumme = 0;
	
	
	
	
	


	public LogicModelNewFundsachen(DataBaseGUI dataBaseGUI, NewFundsachen newFundsachen, UserAHB userAHB, Loading loading) {
		
		this.dataBaseGUI = dataBaseGUI;
		this.newFundsachen = newFundsachen;
		this.userAHB = userAHB;
		this.loading = loading;
		
		
		
		
		//We initialize the value for entryCompleted as false.
//		this.entryCompleted = false;
		

		
		this.okButtonErrorDateLostAndFound.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				errorUnvollstaendigenDaten.dispose();
				
			}
		});
		
		
		this.okButtonErrorDateLostAndFound.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				
				
				errorUnvollstaendigenDaten.dispose();
				
				
			}

			@Override
			public void keyReleased(KeyEvent e) {}
			
		});
		
		
		
	}
	
	
	/**
	 * @description Method to check the correct Date format of datumItemFoundPlaceHolderTextField value entered by the user.
	 */
	public void checkDateFormat() {
		
		System.out.println("checkformat");
		//Regex Format to evaluate the date Format.
		String formatDateRegex = "(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.((?:19|20)[0-9][0-9])$";
		
		this.messageErrorDateLostAndFound = new JLabel("Sie haben ein falsches Datumsformat eingegeben. bitte geben Sie ein korrektes Datumsformat ein(dd.mm.yyyy");
		
		
		this.panelErrorDateLostAndFound = new JPanel(new BorderLayout());
		
		this.panelErrorDateLostAndFound.add(messageErrorDateLostAndFound, BorderLayout.CENTER);
		
		
		if(!Pattern.matches(formatDateRegex, this.newFundsachen.datumItemFoundPlaceHolderTextField.getText())) {

			
			if(!closingNewFundsachen) {
				
						
						SwingUtilities.invokeLater(new Runnable() {
							
							@Override
							public void run() {
								
								errorUnvollstaendigenDaten = new JOptionPane(panelErrorDateLostAndFound, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, errorImg,
										optionButtonErrorDateLostAndFound, null).createDialog("Falsches Datumsformat");
								
								
								errorUnvollstaendigenDaten.setAlwaysOnTop(true);
								errorUnvollstaendigenDaten.setVisible(true);
								
		
								errorUnvollstaendigenDaten.dispose();
								
								
								newFundsachen.datumItemFoundPlaceHolderTextField.requestFocus();

								
								
							}
						});
					
				
						
					}
					
					
					
			
						
						
				
						
			
				
				
				
				
			}
			

		
			
		} 
	
	
	
	
	
	/**
	 * @description Method to set whether the user intends to close or not the NewFundsachen GUI, so we can play with the Lost Focus by the datumItemFoundPlaceHolderTextField.
	 * @param closingNewFundsachen
	 */
	public void setClosingNewFundsachen(boolean closingNewFundsachen ) {
		
		this.closingNewFundsachen = closingNewFundsachen;
	}
	
	
	
	/**
	 * @description This method will check if all required entries are completed correctly.
	 * 
	 * if all the inputs are correct, it will call the method addNewFundsachen in the DaoFundsachenImpl
	 */
	public void checkAllEntries() {
		
	
		//If the Date format is correct the errorDateFormat variable is false then.
		if(!this.errorDateFormat) {
			
			//Check fundItemsJtextfiel
			if(this.newFundsachen.fundItemsJTexfield.getText().equals("") ||
					this.newFundsachen.fundItemsJTexfield.getText().length()< 3) {
				
				this.foundItemCompleted = false;
				this.newFundsachen.fundItemsJTexfield.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
				
			}else {
				this.foundItemCompleted = true;
			}
			
			if(this.newFundsachen.fundOrtJTextField.getText().equals("") ||
					this.newFundsachen.fundOrtJTextField.getText().length()<3) {
				
				this.foundPlaceCompleted = false;
				this.newFundsachen.fundOrtJTextField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
				
			}else {
				
				this.foundPlaceCompleted = true;
			}
			
			
			
			//If by inhaberJTextField  we automatically fill it in with an unknown owner
			if(this.newFundsachen.inhaberJTextField.getText().equals("") ||
					this.newFundsachen.inhaberJTextField.getText().length()<3) {

					this.ownerCompleted = false;
					this.newFundsachen.inhaberJTextField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

			
			}else {
				
				this.ownerCompleted = true;
			}
			
		
			checkKistenNummer();
			
			
			if(this.newFundsachen.kisteNameJTextField.getText().equals("")) {
				
				this.boxnameCompleted = false;
				this.newFundsachen.kisteNameJTextField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
			}else {
				this.boxnameCompleted = true;
			}
			
			
			//Time to save the Information of the Lost and Found items.
			if(!this.foundItemCompleted || !this.foundPlaceCompleted || !this.ownerCompleted || !this.boxnameCompleted ) {
				
				System.out.println("foundItem: " + foundItemCompleted + " foundPlace: " + foundPlaceCompleted + " owner: " + ownerCompleted + " boxname: " + boxnameCompleted);
				
				this.messageErrorDateLostAndFound = new JLabel("Bitte füllen Sie die rot markierten fehlenden Daten aus!");
				
				this.panelErrorDateLostAndFound.add(messageErrorDateLostAndFound, BorderLayout.CENTER);
				
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {

						errorUnvollstaendigenDaten = new JOptionPane(panelErrorDateLostAndFound, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, errorImg,
								optionButtonErrorDateLostAndFound, null).createDialog("Die Daten sind unvollständig!");
						
						errorUnvollstaendigenDaten.setAlwaysOnTop(true);
						errorUnvollstaendigenDaten.setVisible(true);
						
						errorUnvollstaendigenDaten.dispose();
						
					}
				});
				
			}else  {
				
			
				
				//We set the value to the LocalDate variables using the LocalDate.parse method and as parameters the Date inside the PlaceHolderTextField
				//as second argument the DateTimeFormatter with the format we need dd.MM.yyyy for the LocalDate.
				this.lostAndFoundLocalDate = LocalDate.parse(this.newFundsachen.datumItemFoundPlaceHolderTextField.getText(), this.dateTimeFormatter);
				

				//Now for the Date variables we get the value using the Date.valueOf Method and as argument we pass the 
				//LocalDate Variable.
				this.lostAndFoundDate = Date.valueOf(lostAndFoundLocalDate);
				
				
				//First we initialize our instance as a new object of Fundgegenstand
				this.fundgegenstand = new Fundgegenstand();
				
				//Set the values fundgegenstand
				this.fundgegenstand.setDateItemsWasFound(this.lostAndFoundDate);
				this.fundgegenstand.setFoundItems(this.newFundsachen.fundItemsJTexfield.getText());
				this.fundgegenstand.setFundort(this.newFundsachen.fundOrtJTextField.getText());
				this.fundgegenstand.setInhaber(this.newFundsachen.inhaberJTextField.getText());
				this.fundgegenstand.setKisteNummer(this.kisteNumme);
				this.fundgegenstand.setKisteName(this.newFundsachen.kisteNameJTextField.getText());
				this.fundgegenstand.setRueckGabe("");
				this.fundgegenstand.setAbkuerzungMA(this.newFundsachen.abkuerzungMAGeneratedJLabel.getText());
				
				
				this.newFundsachen.dispose();
				
				
				try {
					//Ready to save the data in Database Table FUNDSACHEN

					DAOFundsachen daoFundsachen = new DaoFundsachenImpl(this.userAHB, dataBaseGUI, loading);
					daoFundsachen.addNewFundsachen(fundgegenstand, this.userAHB);
				} catch (DaoException e) {
					e.printStackTrace();
				}

			}
			
					
			
		}
		
	}
	
	
	
	
	
	/**
	 * @description Method to check Box Number so we can set the value automatically to kisteNameJTextField.
	 */
	public void checkKistenNummer() {
		
		//Check
		String kistenNummer = this.newFundsachen.kisteNummerJComboBox.getSelectedItem().toString();
		
		switch (kistenNummer) {
			case "Kistenummer wählen":
				
//				this.entryCompleted = false;
				this.boxnameCompleted = false;
				this.newFundsachen.kisteNummerJComboBox.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
				this.newFundsachen.kisteNameJTextField.setText("");
				
				break;
				
			case "1-Elektro Artikel": 
				
				this.newFundsachen.kisteNameJTextField.setText("Elektro Artikel");
				this.kisteNumme = 1;
				this.boxnameCompleted = true;
				break;
				
			case "2-Schmuck / Brillen":
				this.newFundsachen.kisteNameJTextField.setText("Schmuck / Brillen");
				this.kisteNumme = 2;
				this.boxnameCompleted = true;
				break;
				
			case "3-Kleidung":
				this.newFundsachen.kisteNameJTextField.setText("Kleidung");
				this.kisteNumme = 3;
				this.boxnameCompleted = true;
				break;
				
			case "4-Kosmetik / Badezimmer":
				this.newFundsachen.kisteNameJTextField.setText("Kosmetik / Badezimmer");
				this.kisteNumme = 4;
				this.boxnameCompleted = true;
				break;
				
			case "5-Bücher":
				this.newFundsachen.kisteNameJTextField.setText("Bücher");
				this.kisteNumme = 5;
				this.boxnameCompleted = true;
				break;
				
			case "6-Briefe / Karten jegliche Art":
				this.newFundsachen.kisteNameJTextField.setText("Briefe / Karten jegliche Art");
				this.kisteNumme = 6;
				this.boxnameCompleted = true;
				break;
				
			case "7-Sonstiges":
				this.newFundsachen.kisteNameJTextField.setText("Sonstiges");
				this.kisteNumme = 7;
				this.boxnameCompleted = true;
				break;
			
			
			

			default:
				break;
		}
		
		
	}
	
	
	

}
