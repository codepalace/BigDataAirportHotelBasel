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
		
		//Variables to store the value of each column of the selected row in the JTable
		int id = 0;
		Date dateItemsWasFound = null;
		String foundItems = "";
		String fundort = "";
		String inhaber = "";
		String kisteN = "";
		String kisteName = "";
		String rueckGabe = "";
		String abkuerzungMA = "";

		if(selectedRow!=-1) { //When it we have a selected row.
			
			/*
			 * - Each variable receive the value Casting first the type of value we want to store in the variable;
			 * - Then we call the method getValueAt using the variable TableModel(model);
			 * - As first argument we pass the selectedRow(Argument for the rowIndex and for the second argument the Table Column(columnIndex);
			 * - The columnIndex has a type value stored in dataBase. 
			 */
			id = (int)model.getValueAt(selectedRow, 0);
			dateItemsWasFound = (Date)model.getValueAt(selectedRow, 1);
			foundItems = (String)model.getValueAt(selectedRow, 2);
			fundort = (String)model.getValueAt(selectedRow, 3);
			inhaber = (String)model.getValueAt(selectedRow, 4);
			
			
			/*
			 * kisteN(String) store the value of kisteNummer as String
			 */
			kisteN = String.valueOf(model.getValueAt(selectedRow, 5));
			
			
			kisteName = (String)model.getValueAt(selectedRow, 6);
			rueckGabe = (String)model.getValueAt(selectedRow, 7);
			abkuerzungMA = (String)model.getValueAt(selectedRow, 8);
			

		
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
					
					kisteName = "Elektro Artikel";
					
					break;
					
				case "2-Schmuck / Brillen":
					
					kisteN = "2"; 
					
					kisteNummer = Integer.parseInt(kisteN);
					
					model.setValueAt(kisteN, selectedRow, 5);
					model.setValueAt("Schmuck / Brillen", selectedRow, 6);
					
					kisteName = "Schmuck / Brillen";

					break;
					
				
				 case "3-Kleidung":
					 
					
					kisteN = "3"; 
					
					kisteNummer = Integer.parseInt(kisteN);
					
					model.setValueAt(kisteN, selectedRow, 5);
					model.setValueAt("Kleidung", selectedRow, 6);
					
					kisteName = "Kleidung";

					break;
					
					
				 case "4-Kosmetik / Badezimmer":
						
						kisteN = "4"; 
						
						kisteNummer = Integer.parseInt(kisteN);
						
						model.setValueAt(kisteN, selectedRow, 5);
						model.setValueAt("Kosmetik / Badezimmer", selectedRow, 6);
						
						kisteName = "Kosmetik / Badezimmer";

						break;
						
						
				 case "5-Bücher":
						
						kisteN = "5"; 
						
						kisteNummer = Integer.parseInt(kisteN);
						
						model.setValueAt(kisteN, selectedRow, 5);
						model.setValueAt("Bücher", selectedRow, 6);
						
						kisteName = "Bücher";

						break;
						
						
				 case "6-Briefe / Karten jegliche Art":
						
						kisteN = "6"; 
						
						kisteNummer = Integer.parseInt(kisteN);
						
						model.setValueAt(kisteN, selectedRow, 5);
						model.setValueAt("Briefe / Karten jegliche Art", selectedRow, 6);
						
						kisteName = "Briefe / Karten jegliche Art";

						break;
						
				 case "7-Sonstiges":
						
						kisteN = "7"; 
						
						kisteNummer = Integer.parseInt(kisteN);
						
						model.setValueAt(kisteN, selectedRow, 5);
						model.setValueAt("Sonstiges", selectedRow, 6);
						
						kisteName = "Sonstiges";

						break;
						
				 case "8-Kiste ohne Namen / Angaben":
						
						kisteN = "8"; 
						
						kisteNummer = Integer.parseInt(kisteN);
						
						model.setValueAt(kisteN, selectedRow, 5);
						model.setValueAt("Kiste ohne Namen / Angaben", selectedRow, 6);
						
						kisteName = "Kiste ohne Namen / Angaben";

						break;
						
				default:
					break;
			}
		}
		
		
		

		
		
		
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
		DAOFundsachen daoFundsachen = new DaoFundsachenImpl(getUserAHB(), dataBaseGUI, loading, this);
		
		try {
			//Call to updateFundsachen passing the argument fundgegenstand containing all the data
			daoFundsachen.updateFundsachen(fundgegenstand);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		}
		
	
	
			
		
		

	}
	
	
	

	
	
	@Override
	public void searchResultsInDataBase(DataBaseGUI dataBaseGUI) {
		
		//we call the super Method
		super.searchResultsInDataBase(dataBaseGUI);
		
		//Set value for the dataBaseGUI
		LogicModelFundSachen.dataBaseGUI = dataBaseGUI;

		
		//Block switch conditional to evaluate what we are going to search in database
		switch (getSearchSelected()) {
			case "Suchen nach Datum":
				
				/* First we have to evaluate if the date format is correct.
				 * 
				 * For that we are going to call the checkDateFormatToSearchInDataBase Method from the super Class LogicModel. 
				 * 
				 * if return true. The Date format is correct 
				 */

				if(checkDateFormatToSearchInDataBase()) {

					
					//Set the value of loading object, First argument the GUI in Background and true to block it.
					this.loading = new Loading(dataBaseGUI, true);
					
					//Instance of DAOFunsachen
					DAOFundsachen daoFundsachen = new DaoFundsachenImpl(getUserAHB(), LogicModelFundSachen.dataBaseGUI, loading, this);
					
					
					
					try {
						//Now we are ready to call searchByDateFundschen Method by the DAO Object.
						daoFundsachen.searchByDateFundsachen(getDateToSearchInDataBase());
					} catch (DaoException e1) {
						e1.printStackTrace();
					}
				}
				
	
				break;
				
			case "Suchen nach Fundsachen":
				
			
				suchenNachFundsachen(getToSearch());
				break;
				
			case "Suchen nach Namen":
				suchenNachNamen(getToSearch());
				break;
				
				
			case "Suchen nach Fundort":
				suchenNachFundort(getToSearch());
				break;

			default:
				break;
		}
		
		
	}
	
	

	
	
	

	/**
	 * @description Method to search by Lost and found items.
	 * @param fundsachen
	 */
	private void suchenNachFundsachen(String fundsachen) {
		
		
		//Instance of DAOFunsachen
		DAOFundsachen daoFundsachen = new DaoFundsachenImpl(getUserAHB(), dataBaseGUI, new Loading(dataBaseGUI, true), this);
		
		
		
		try {
			//Now we are ready to call seachByLostAndFound Method by the DAO Object.
			daoFundsachen.searchByLostAndFound(fundsachen);
		} catch (DaoException e1) {
			e1.printStackTrace();
		}
		
	}
	
	
	
	
	/**
	 * @description Method to search by Guest name
	 * @param namen
	 */
	private void suchenNachNamen(String namen) {
		
		// Instance of DAOFunsachen
		DAOFundsachen daoFundsachen = new DaoFundsachenImpl(getUserAHB(), dataBaseGUI, new Loading(dataBaseGUI, true), this);

		try {
			// Now we are ready to call searchByGuestName Method by the DAO Object.
			daoFundsachen.searchByGuestName(namen);
		} catch (DaoException e1) {
			e1.printStackTrace();
		}
	}
	
	
	
	/**
	 * @description Method to search by foundPlace where the items was found.
	 * @param fundOrt
	 */
	private void suchenNachFundort(String fundOrt) {
		
		// Instance of DAOFunsachen
		DAOFundsachen daoFundsachen = new DaoFundsachenImpl(getUserAHB(), dataBaseGUI, new Loading(dataBaseGUI, true), this);
		
		try {
			// Now we are ready to call suchenNachFundort Method by the DAO Object.
			daoFundsachen.suchenNachFundort(fundOrt);
		} catch (DaoException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * @description Method to reload Lost and Found Data and displaying all in the JTable
	 * @param dataBaseGUI
	 */
	public void reloadFundsachen(DataBaseGUI dataBaseGUI, Loading loading) {

		
		LogicModelFundSachen.dataBaseGUI = dataBaseGUI;
		
		//Instance of DAOFunsachen
		DAOFundsachen daoFundsachen = new DaoFundsachenImpl(getUserAHB(), LogicModelFundSachen.dataBaseGUI, loading, this);

				
				
				
				try {
					//Now we are ready to call realoadFundsachenData Method by the DAO Object.
					daoFundsachen.reloadFundsachenData();
				} catch (DaoException e1) {
					e1.printStackTrace();
				}
	}
	
	

}
