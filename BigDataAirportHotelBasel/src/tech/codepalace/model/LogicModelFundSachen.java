package tech.codepalace.model;

import java.awt.EventQueue;
import java.sql.Date;

import javax.swing.table.TableModel;

import tech.codepalace.controller.NewFundsachenController;
import tech.codepalace.dao.DAOFundsachen;
import tech.codepalace.dao.DaoException;
import tech.codepalace.dao.DaoFundsachenImpl;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;
import tech.codepalace.view.frames.NewFundsachen;


/**
 * @description Logic Class for the FundSachen GUI.
 * @author Antonio Estevez Gonzalez
 *
 */
public class LogicModelFundSachen extends LogicModel {
	

	

	
	private static DataBaseGUI dataBaseGUI;
	private Loading loading;
	
	private String  abkuerzungMA = "";
	
	private DataEncryption dataEncryption;
	
	//Variable for the Lost and Found
	private Fundgegenstand fundgegenstand;
	
	
	
	
	public LogicModelFundSachen(DataBaseGUI dataBaseGUI, Loading loading) {
		LogicModelFundSachen.dataBaseGUI = dataBaseGUI;
		this.loading = loading;
		
		//We create a new Instance fo DataEncryption, needed to decrypt some Data we need.
		this.dataEncryption = new DataEncryption();
		
		
	}
	
	public LogicModelFundSachen() {
		
		//We create a new Instance fo DataEncryption, needed to decrypt some Data we need.
		this.dataEncryption = new DataEncryption();
				
	}
	
	
	
	
	public void enterNewFoundsachenEntries(DataBaseGUI dataBaseGUI) {
		
		
		
		LogicModelFundSachen.dataBaseGUI = dataBaseGUI;
		
		
		try {
			/* With the help of the dataEncryption object we set the value to the variable abkuerzungMA, calling to decryptData and for that we 
			 * also call getUserAHB().getAbbkuerzungMA() to get the value we need. 
			 * 
			 * AbbkuerzungMA is the Team user who is typing the new entries in the database.
			 *
			 * 
			 */
		    abkuerzungMA = this.dataEncryption.decryptData(getUserAHB().getAbkuerzungMA());
		    
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				

				NewFundsachen newFundsachen = new NewFundsachen(LogicModelFundSachen.dataBaseGUI, true, abkuerzungMA);
				
				LogicModelNewFundsachen logicModelFundSachen = new LogicModelNewFundsachen(dataBaseGUI, newFundsachen, getUserAHB(), loading);
				
				new NewFundsachenController(newFundsachen, logicModelFundSachen);
				
				newFundsachen.setLocationRelativeTo(null);
				
				newFundsachen.setVisible(true);
			}
		});

		
		
	}
	
	
	/**
	 * @description Method that receives the Data to be update in the DataBase.
	 * @param selectedRow
	 * @param model
	 */
	public void updateFundsachen(int selectedRow, TableModel model, DataBaseGUI dataBaseGUI) {
		
		LogicModelFundSachen.dataBaseGUI = dataBaseGUI;

		
		//Initialize Fundgegenstand
		this.fundgegenstand = new Fundgegenstand();
		

		/*Variables to save the values got it from the TableModel received in the Method arguments.
		
		selectedRow argument(int) got from the JTable.getSelectedRow call Method.
		
		databaseGUI needed to call the DAO Object for updating the data in the Database(FUNDSACHEN table). 
		
		Each variables below get the Casting value from model.getValueAt(The selectedRow, and the Corresponding column with the value Tip).
		*/
		int id = (int)model.getValueAt(selectedRow, 0);
		Date dateItemsWasFound = (Date)model.getValueAt(selectedRow, 1);
		String foundItems = (String)model.getValueAt(selectedRow, 2);
		String fundort = (String)model.getValueAt(selectedRow, 3);
		String inhaber = (String)model.getValueAt(selectedRow, 4);
		
		//For this variable we get the int value and save it in one String
		/*
		 * The 2 variables(instruction below we have to check to remove the space to avoid errors. 
		 * 
		 * But i am thinking to insert a Dropdownlist in the JTable
		 */
		String kisteN = String.valueOf(model.getValueAt(selectedRow, 5));
		
		//This variable will have the parseInt(String) value over here and below was necessary to avoid Casting error.
		int kisteNummer = 0;
		
		/*
		 * Now we are using JComboBox by the Column 5 in the fundsachenTable as DefaultCellEditor.
		 * 
		 * The JComboBox contains various String values to be selected, thats why we use one try catch(NumberFormatException).
		 * 
		 * When we catch the NumberFormatException inside the catch block i use one switch conditional to evaluate
		 * the selected item. Selected item value will be set by the JTable selectedRow, Column 5 and this value
		 * will be saved over by String kisteN. 
		 * 
		 * the variable kisteNummer int is initialized 0. 
		 * 
		 * We try to set a new value to kisteNummer using parseInt Method and kisteN giving as argument.
		 * 
		 * While kisteN at the Moment is not a Number it will come one NumberFormatException in the game.
		 * 
		 * Thats why inside the catch block we evaluate with one switch conditional the Value of kistN so we 
		 * can modify the hole String for only one String number to be parseInt correctly.
		 */
		try {
			kisteNummer = Integer.parseInt(kisteN);
		} catch (NumberFormatException e) {
			
			//Time to catch the NumberFormatException and do something to get the result what we need.
			switch (kisteN) { //evaluate kisteN
				
				case "1-Elektro Artikel": //case this value that was applied from the selected JComboBox item.
					
					kisteN = "1"; //We set new value only Number as String
					
					kisteNummer = Integer.parseInt(kisteN); //now kisteNummer receive new value parseInt(kisteN)
					
					model.setValueAt(kisteN, selectedRow, 5);//We set the new value to the Column 5 in the JTable
					
					//We set also the value for the column6
					model.setValueAt("Elektro Artikel", selectedRow, 6);
					
					
					break;
					
				case "2-Schmuck / Brillen":
					
					kisteN = "2"; 
					
					kisteNummer = Integer.parseInt(kisteN);
					
					model.setValueAt(kisteN, selectedRow, 5);
					model.setValueAt("Schmuck / Brillen", selectedRow, 6);

					break;
					
				
				 case "3-Kleidung":
					
					kisteN = "3"; 
					
					kisteNummer = Integer.parseInt(kisteN);
					
					model.setValueAt(kisteN, selectedRow, 5);
					model.setValueAt("Kleidung", selectedRow, 6);

					break;
					
					
				 case "4-Kosmetik / Badezimmer":
						
						kisteN = "4"; 
						
						kisteNummer = Integer.parseInt(kisteN);
						
						model.setValueAt(kisteN, selectedRow, 5);
						model.setValueAt("Kosmetik / Badezimmer", selectedRow, 6);

						break;
						
						
				 case "5-Bücher":
						
						kisteN = "5"; 
						
						kisteNummer = Integer.parseInt(kisteN);
						
						model.setValueAt(kisteN, selectedRow, 5);
						model.setValueAt("Bücher", selectedRow, 6);

						break;
						
						
				 case "6-Briefe / Karten jegliche Art":
						
						kisteN = "6"; 
						
						kisteNummer = Integer.parseInt(kisteN);
						
						model.setValueAt(kisteN, selectedRow, 5);
						model.setValueAt("Briefe / Karten jegliche Art", selectedRow, 6);

						break;
						
				 case "7-Sonstiges":
						
						kisteN = "7"; 
						
						kisteNummer = Integer.parseInt(kisteN);
						
						model.setValueAt(kisteN, selectedRow, 5);
						model.setValueAt("Sonstiges", selectedRow, 6);

						break;
						
				 case "8-Kiste ohne Namen / Angaben":
						
						kisteN = "8"; 
						
						kisteNummer = Integer.parseInt(kisteN);
						
						model.setValueAt(kisteN, selectedRow, 5);
						model.setValueAt("Kiste ohne Namen / Angaben", selectedRow, 6);

						break;
						
				default:
					break;
			}
		}
		
		
		

		String kisteName = (String)model.getValueAt(selectedRow, 6);
		String rueckGabe = (String)model.getValueAt(selectedRow, 7);
		String abkuerzungMA = (String)model.getValueAt(selectedRow, 8);
		
		
		//fundgegenstand set values to be sent to the dao Object.
		this.fundgegenstand.setId(id);
		this.fundgegenstand.setDateItemsWasFound(dateItemsWasFound);
		this.fundgegenstand.setFoundItems(foundItems);
		this.fundgegenstand.setFundort(fundort);
		this.fundgegenstand.setInhaber(inhaber);
		this.fundgegenstand.setKisteNummer(kisteNummer);
		this.fundgegenstand.setKisteName(kisteName);
		this.fundgegenstand.setRueckGabe(rueckGabe);
		this.fundgegenstand.setAbkuerzungMA(abkuerzungMA);
		
		//Instance of DAOFunsachen
		DAOFundsachen daoFundsachen = new DaoFundsachenImpl(getUserAHB(), dataBaseGUI, loading);
		
		try {
			//Call to updateFundsachen passing the argument fundgegenstand containing all the data
			daoFundsachen.updateFundsachen(fundgegenstand);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		

	}
	
	
	

}
